// Script for Overwolf Warning Page

document.addEventListener('DOMContentLoaded', function() {
  // Make tooltips accessible via keyboard and clicks
  const tooltips = document.querySelectorAll('.tooltip');
  
  tooltips.forEach(tooltip => {
    // Add tabindex to make tooltips focusable
    tooltip.setAttribute('tabindex', '0');
    
    // Show tooltip on focus (for keyboard navigation)
    tooltip.addEventListener('focus', function() {
      const tooltipContent = this.querySelector('.tooltip-content');
      tooltipContent.style.visibility = 'visible';
      tooltipContent.style.opacity = '1';
    });
    
    // Hide tooltip when focus is lost
    tooltip.addEventListener('blur', function() {
      const tooltipContent = this.querySelector('.tooltip-content');
      tooltipContent.style.visibility = 'hidden';
      tooltipContent.style.opacity = '0';
    });
    
    // Toggle tooltip on click (for mobile devices)
    tooltip.addEventListener('click', function(e) {
      e.preventDefault();
      const tooltipContent = this.querySelector('.tooltip-content');
      
      if (tooltipContent.style.visibility === 'visible') {
        tooltipContent.style.visibility = 'hidden';
        tooltipContent.style.opacity = '0';
      } else {
        tooltipContent.style.visibility = 'visible';
        tooltipContent.style.opacity = '1';
      }
    });
  });
  
  // Language selector functionality (if implemented)
  const languageSelector = document.getElementById('language-selector');
  if (languageSelector) {
    languageSelector.addEventListener('change', function() {
      window.location.href = this.value;
    });
  }
});