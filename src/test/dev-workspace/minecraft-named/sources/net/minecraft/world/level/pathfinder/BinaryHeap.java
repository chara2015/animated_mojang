package net.minecraft.world.level.pathfinder;

import java.util.Arrays;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/pathfinder/BinaryHeap.class */
public class BinaryHeap {
    private Node[] heap = new Node[128];
    private int size;

    public Node insert(Node $$0) {
        if ($$0.heapIdx >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        if (this.size == this.heap.length) {
            Node[] $$1 = new Node[this.size << 1];
            System.arraycopy(this.heap, 0, $$1, 0, this.size);
            this.heap = $$1;
        }
        this.heap[this.size] = $$0;
        $$0.heapIdx = this.size;
        int i = this.size;
        this.size = i + 1;
        upHeap(i);
        return $$0;
    }

    public void clear() {
        this.size = 0;
    }

    public Node peek() {
        return this.heap[0];
    }

    public Node pop() {
        Node $$0 = this.heap[0];
        Node[] nodeArr = this.heap;
        Node[] nodeArr2 = this.heap;
        int i = this.size - 1;
        this.size = i;
        nodeArr[0] = nodeArr2[i];
        this.heap[this.size] = null;
        if (this.size > 0) {
            downHeap(0);
        }
        $$0.heapIdx = -1;
        return $$0;
    }

    public void remove(Node $$0) {
        Node[] nodeArr = this.heap;
        int i = $$0.heapIdx;
        Node[] nodeArr2 = this.heap;
        int i2 = this.size - 1;
        this.size = i2;
        nodeArr[i] = nodeArr2[i2];
        this.heap[this.size] = null;
        if (this.size > $$0.heapIdx) {
            if (this.heap[$$0.heapIdx].f < $$0.f) {
                upHeap($$0.heapIdx);
            } else {
                downHeap($$0.heapIdx);
            }
        }
        $$0.heapIdx = -1;
    }

    public void changeCost(Node $$0, float $$1) {
        float $$2 = $$0.f;
        $$0.f = $$1;
        if ($$1 < $$2) {
            upHeap($$0.heapIdx);
        } else {
            downHeap($$0.heapIdx);
        }
    }

    public int size() {
        return this.size;
    }

    private void upHeap(int $$0) {
        Node $$1 = this.heap[$$0];
        float $$2 = $$1.f;
        while ($$0 > 0) {
            int $$3 = ($$0 - 1) >> 1;
            Node $$4 = this.heap[$$3];
            if ($$2 >= $$4.f) {
                break;
            }
            this.heap[$$0] = $$4;
            $$4.heapIdx = $$0;
            $$0 = $$3;
        }
        this.heap[$$0] = $$1;
        $$1.heapIdx = $$0;
    }

    private void downHeap(int $$0) {
        Node $$9;
        float $$10;
        Node $$1 = this.heap[$$0];
        float $$2 = $$1.f;
        while (true) {
            int $$3 = 1 + ($$0 << 1);
            int $$4 = $$3 + 1;
            if ($$3 < this.size) {
                Node $$5 = this.heap[$$3];
                float $$6 = $$5.f;
                if ($$4 >= this.size) {
                    $$9 = null;
                    $$10 = Float.POSITIVE_INFINITY;
                } else {
                    $$9 = this.heap[$$4];
                    $$10 = $$9.f;
                }
                if ($$6 >= $$10) {
                    if ($$10 >= $$2) {
                        break;
                    }
                    this.heap[$$0] = $$9;
                    $$9.heapIdx = $$0;
                    $$0 = $$4;
                } else {
                    if ($$6 >= $$2) {
                        break;
                    }
                    this.heap[$$0] = $$5;
                    $$5.heapIdx = $$0;
                    $$0 = $$3;
                }
            } else {
                break;
            }
        }
        this.heap[$$0] = $$1;
        $$1.heapIdx = $$0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Node[] getHeap() {
        return (Node[]) Arrays.copyOf(this.heap, this.size);
    }
}
