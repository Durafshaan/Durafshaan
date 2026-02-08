// Form validation utilities
export const validateEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

export const validateRequired = (value) => {
  return value && value.trim().length > 0;
};

export const validateMinLength = (value, minLength) => {
  return value && value.trim().length >= minLength;
};

export const validateMaxLength = (value, maxLength) => {
  return !value || value.trim().length <= maxLength;
};

export const validateContactForm = (formData) => {
  const errors = {};

  // Name validation
  if (!validateRequired(formData.name)) {
    errors.name = 'Name is required';
  } else if (!validateMinLength(formData.name, 2)) {
    errors.name = 'Name must be at least 2 characters';
  } else if (!validateMaxLength(formData.name, 50)) {
    errors.name = 'Name must be less than 50 characters';
  }

  // Email validation
  if (!validateRequired(formData.email)) {
    errors.email = 'Email is required';
  } else if (!validateEmail(formData.email)) {
    errors.email = 'Please enter a valid email address';
  }

  // Subject validation
  if (!validateRequired(formData.subject)) {
    errors.subject = 'Subject is required';
  } else if (!validateMinLength(formData.subject, 5)) {
    errors.subject = 'Subject must be at least 5 characters';
  } else if (!validateMaxLength(formData.subject, 100)) {
    errors.subject = 'Subject must be less than 100 characters';
  }

  // Message validation
  if (!validateRequired(formData.message)) {
    errors.message = 'Message is required';
  } else if (!validateMinLength(formData.message, 10)) {
    errors.message = 'Message must be at least 10 characters';
  } else if (!validateMaxLength(formData.message, 1000)) {
    errors.message = 'Message must be less than 1000 characters';
  }

  return {
    isValid: Object.keys(errors).length === 0,
    errors
  };
};

// Sanitize input to prevent XSS
export const sanitizeInput = (input) => {
  if (typeof input !== 'string') return input;
  
  return input
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#x27;')
    .replace(/\//g, '&#x2F;');
};

// Format form data before submission
export const formatFormData = (formData) => {
  return {
    name: sanitizeInput(formData.name?.trim()),
    email: sanitizeInput(formData.email?.trim().toLowerCase()),
    subject: sanitizeInput(formData.subject?.trim()),
    message: sanitizeInput(formData.message?.trim())
  };
};

