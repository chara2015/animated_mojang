package net.labymod.api.client.gui.screen.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.injector.LssInjector;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/StyledWidget.class */
public abstract class StyledWidget implements Widget {
    private static final LssInjector LSS_INJECTOR = Laby.references().lssInjector();
    protected final List<StyleSheet> styleSheets = new ArrayList();
    protected final List<StyleInstructions> styleInstructions = new ArrayList();
    protected final List<StyleInstructions> dynamicStyleInstructions = new ArrayList();
    private final Set<String> patchedKeys = new HashSet();
    private boolean hasStateAttributes = false;
    private boolean hasParentStateAttributes = false;
    protected boolean preventStateUpdate = false;
    protected Widget actualWidget = this;
    private final Map<String, LssVariable> variablesForUpdate = new HashMap();

    protected StyledWidget() {
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void reset() {
        this.styleSheets.clear();
        this.styleInstructions.clear();
        this.hasStateAttributes = false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void applyStyleSheet(StyleSheet styleSheet) {
        applyStyleSheetInternal(styleSheet);
    }

    protected void reloadStyleSheets() {
        reloadStyleSheets(true);
    }

    protected void reloadStyleSheets(boolean updateParent) {
        StyleSheet[] styleSheets = (StyleSheet[]) this.styleSheets.toArray(new StyleSheet[0]);
        this.styleSheets.clear();
        for (Widget child : getChildren()) {
            if (child instanceof StyledWidget) {
                ((StyledWidget) child).reloadStyleSheets(updateParent);
            }
        }
        applyStyleSheets(styleSheets, true, updateParent);
    }

    @ApiStatus.Internal
    protected void applyStyleSheetInternal(StyleSheet styleSheet) {
        if (this.styleSheets.contains(styleSheet)) {
            return;
        }
        this.styleSheets.add(styleSheet);
        styleSheet.applyToWidget(this);
        List<StyleSheet> styleSheetInjectors = LSS_INJECTOR.getMatchedStyleSheetInjectors(styleSheet);
        this.styleSheets.addAll(styleSheetInjectors);
        for (StyleSheet injectorStyleSheet : styleSheetInjectors) {
            injectorStyleSheet.applyToWidget(this);
        }
        List<StyleSheet> widgetInjectors = LSS_INJECTOR.getMatchedWidgetInjectors(this);
        this.styleSheets.addAll(widgetInjectors);
        for (StyleSheet injectorStyleSheet2 : widgetInjectors) {
            injectorStyleSheet2.applyToWidget(this);
        }
        Collections.sort(this.styleSheets);
    }

    protected void applyStyleSheets(StyleSheet[] styleSheets) {
        applyStyleSheets(styleSheets, false);
    }

    protected void applyStyleSheets(StyleSheet[] styleSheets, boolean direct) {
        applyStyleSheets(styleSheets, direct, true);
    }

    protected void applyStyleSheets(StyleSheet[] styleSheets, boolean direct, boolean updateParent) {
        for (StyleSheet styleSheet : styleSheets) {
            if (direct) {
                applyStyleSheetInternal(styleSheet);
            } else {
                applyStyleSheet(styleSheet);
            }
        }
        postStyleSheetLoad();
        updateState(true);
        updateBounds();
        handleAttributes();
        if (updateParent && (getParent() instanceof Widget)) {
            ((Widget) getParent()).updateBounds();
            ((Widget) getParent()).handleAttributes();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void updateState(boolean force) {
        if (this.preventStateUpdate) {
            return;
        }
        if (force) {
            patchAttributes();
        }
        if (this.hasStateAttributes || force) {
            patchAttributes();
            for (Widget child : getChildren()) {
                if (child.hasParentStateAttributes()) {
                    child.updateState(force);
                }
            }
            updateBounds();
            handleAttributes();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void resetState() {
        for (StyleInstructions styleInstruction : this.styleInstructions) {
            styleInstruction.patch().unpatch(this);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void patchAttributes() {
        this.patchedKeys.clear();
        patchAttributes(getSortedStyleInstructions());
        patchAttributes(getDynamicSortedStyleInstructions());
    }

    private void patchAttributes(Collection<StyleInstructions> instructions) {
        for (StyleInstructions instruction : instructions) {
            patchAttribute(instruction);
        }
    }

    private void patchAttribute(StyleInstructions instruction) {
        if (instruction.selector().match(instruction.getSkipDepth(), this, true)) {
            patch(instruction.patch());
            this.patchedKeys.add(instruction.patch().getKey());
        } else if (!this.patchedKeys.contains(instruction.patch().getKey()) && instruction.selector().match(instruction.getSkipDepth(), this, false)) {
            instruction.patch().unpatch(this);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void patch(AttributePatch patch) {
        patch.patch(this);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void addAttributePatch(Selector selector, AttributePatch patch, int skipDepth) {
        StyleInstructions styleInstruction = new StyleInstructions(selector, patch, skipDepth);
        if (this.styleInstructions.contains(styleInstruction)) {
            return;
        }
        this.styleInstructions.add(styleInstruction);
        try {
            this.styleInstructions.sort(StyleInstructions.COMPARATOR);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (!selector.hasStateAttributes() && CollectionHelper.noneMatch(this.styleInstructions, styleInstructions -> {
            return styleInstructions.patch().equalsKey(patch) && styleInstructions.selector().hasStateAttributes();
        })) {
            ListIterator<StyleInstructions> iterator = this.styleInstructions.listIterator(this.styleInstructions.size());
            boolean found = false;
            while (iterator.hasPrevious()) {
                StyleInstructions previous = iterator.previous();
                if (previous.patch().equals(patch) || (previous.patch().equalsKey(patch) && !previous.selector().hasStateAttributes() && previous.patch().instruction().styleSheet().getPriority() != patch.instruction().styleSheet().getPriority())) {
                    if (found) {
                        iterator.remove();
                    } else {
                        found = true;
                    }
                }
            }
        }
        if (selector.hasStateAttributes()) {
            this.hasStateAttributes = true;
            if (selector.hasParentStateAttributes()) {
                this.hasParentStateAttributes = true;
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getStyleInstructions() {
        return this.styleInstructions;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getSortedStyleInstructions() {
        return this.styleInstructions;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getDynamicStyleInstructions() {
        return this.dynamicStyleInstructions;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getDynamicSortedStyleInstructions() {
        return this.dynamicStyleInstructions;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasParentStateAttributes() {
        return this.hasParentStateAttributes;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Widget actualWidget() {
        return this.actualWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void applyMediaRules(boolean updateState) {
        this.dynamicStyleInstructions.clear();
        for (StyleSheet styleSheet : this.styleSheets) {
            List<StyleInstructions> matchingMediaRules = styleSheet.getMatchingMediaRules(this);
            if (matchingMediaRules != null) {
                this.dynamicStyleInstructions.addAll(matchingMediaRules);
            }
        }
        if (!this.dynamicStyleInstructions.isEmpty() && updateState) {
            try {
                updateState(true);
            } catch (StackOverflowError e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLssVariable(LssVariable variable) {
        ThreadSafe.ensureRenderThread();
        this.variablesForUpdate.put(variable.key(), variable);
    }

    public void forceUpdateLssVariable(LssVariable variable) {
        refreshProperties(variable);
        this.variablesForUpdate.remove(variable.key());
    }

    private void refreshProperties(LssVariable variable) {
        refreshProperties(patch -> {
            return requiresUpdate(patch.element(), variable);
        });
    }

    private void refreshProperties(Map<String, LssVariable> variables) {
        if (variables.size() == 1) {
            refreshProperties(variables.values().iterator().next());
        } else {
            refreshProperties(patch -> {
                Map<String, String> patchVariables = patch.collectVariables(this);
                if (patchVariables.isEmpty()) {
                    return false;
                }
                for (String variable : variables.keySet()) {
                    if (patchVariables.containsKey(variable)) {
                        return true;
                    }
                }
                return false;
            });
        }
    }

    private void refreshProperties(Predicate<PropertyAttributePatch> predicate) {
        boolean updated = false;
        for (StyleInstructions styleInstruction : this.styleInstructions) {
            AttributePatch patch = styleInstruction.patch();
            if ((patch instanceof PropertyAttributePatch) && predicate.test((PropertyAttributePatch) patch)) {
                updated = true;
            }
        }
        if (updated) {
            patchAttributes();
            updateState(false);
            handleAttributes();
        }
    }

    private boolean requiresUpdate(Element element, LssVariable variable) {
        if (!(element instanceof Function)) {
            return false;
        }
        Function function = (Function) element;
        if (function.getName().equals("var") && function.parameters().length == 1) {
            return function.parameters()[0].getRawValue().equals(variable.key());
        }
        for (Element parameter : function.parameters()) {
            if (requiresUpdate(parameter, variable)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        if (!this.variablesForUpdate.isEmpty()) {
            refreshProperties(this.variablesForUpdate);
            this.variablesForUpdate.clear();
        }
    }

    public List<StyleSheet> getStyleSheets() {
        return this.styleSheets;
    }
}
