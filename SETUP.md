# Setup Guide for Durafshaan's Portfolio Website

This guide will help you set up and customize the modern frontend portfolio website.

## 🚀 Quick Start

### 1. Prerequisites
- Node.js (v16 or higher)
- npm or yarn
- Git

### 2. Installation Steps

```bash
# Clone the repository
git clone https://github.com/Durafshaan/Durafshaan.git
cd Durafshaan

# Install dependencies
npm install

# Start development server
npm run dev
```

## 🎨 Customization Guide

### Step 1: Personal Information
Edit the following files to add your personal information:

1. **About Section** (`src/components/About.jsx`):
   - Update skills array
   - Modify interests
   - Change profile information

2. **Hero Section** (`src/components/Hero3D.jsx`):
   - Update name and description
   - Modify social links
   - Customize call-to-action buttons

3. **Timeline** (`src/components/Timeline.jsx`):
   - Update timeline items with your journey
   - Modify achievements
   - Add your learning milestones

4. **Projects** (`src/components/Projects.jsx`):
   - Replace sample projects with your actual projects
   - Update GitHub links
   - Add project images

5. **Contact** (`src/components/Contact.jsx`):
   - Update contact information
   - Modify email address
   - Update social links (GitHub, LinkedIn)

### Adding Your Images

#### Option 1: Local Images
1. Place images in `src/assets/images/`
2. Import and use in components:
```javascript
import profileImage from '../assets/images/profile.jpg';
```

#### Option 2: Firebase Storage
1. Upload images to Firebase Storage
2. Get download URLs
3. Use URLs in components

### Styling Customization

#### Colors
Edit `tailwind.config.js` to change the color scheme:
```javascript
colors: {
  primary: {
    // Your custom colors
    500: '#your-color',
    600: '#your-darker-color',
  }
}
```

#### Animations
Modify animations in `src/utils/animations.js` or `src/styles/global.css`.

## 🚀 Deployment

### Firebase Hosting
```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login to Firebase
firebase login

# Initialize Firebase in your project
firebase init

# Build the project
npm run build

# Deploy to Firebase
firebase deploy
```

### Alternative Deployment Options
- **Vercel**: Connect GitHub repo to Vercel
- **Netlify**: Drag and drop dist folder or connect GitHub
- **GitHub Pages**: Use GitHub Actions for deployment

## 📊 Adding Content via Firebase

### Projects
Add projects to Firestore collection `projects`:
```javascript
{
  title: "Project Name",
  description: "Project description",
  technologies: ["React", "Firebase"],
  category: "web", // or "mobile", "backend"
  github: "https://github.com/username/repo",
  demo: "https://demo-url.com",
  featured: true,
  createdAt: timestamp
}
```

### Skills
Add skills to Firestore collection `skills`:
```javascript
{
  name: "JavaScript",
  level: 85,
  category: "frontend",
  description: "Programming language",
  createdAt: timestamp
}
```

## 🔒 Security Configuration

### Firestore Rules
The included `firestore.rules` file provides basic security:
- Public read access to projects and skills
- Write access only for contact forms
- Admin-only access for content management

### Storage Rules
The `storage.rules` file allows:
- Public read access to all files
- Restricted write access (admin only)

## 🐛 Troubleshooting

### Common Issues

1. **Firebase not configured**:
   - Check if config values are correct
   - Ensure Firebase services are enabled

2. **3D elements not loading**:
   - Check browser WebGL support
   - Ensure Three.js dependencies are installed

3. **Build errors**:
   - Clear node_modules and reinstall
   - Check for missing dependencies

4. **Deployment issues**:
   - Verify Firebase project ID in `.firebaserc`
   - Check build output in `dist` folder

### Getting Help
- Check the main README.md for additional information
- Review Firebase documentation
- Check browser console for errors
- Ensure all dependencies are properly installed

## 📈 Performance Optimization

### Image Optimization
- Use WebP format for better compression
- Implement lazy loading for images
- Optimize image sizes for different screen sizes

### Code Splitting
- The project uses Vite for automatic code splitting
- Consider lazy loading heavy components

### Firebase Optimization
- Use Firebase indexes for better query performance
- Implement caching strategies
- Monitor Firebase usage in console

## 🎯 Next Steps

After setup, consider:
1. Adding Google Analytics for visitor tracking
2. Implementing SEO optimizations
3. Adding a blog section
4. Creating an admin panel for content management
5. Adding more interactive 3D elements
6. Implementing dark/light theme toggle

---

Need help? Check the main README.md or create an issue on GitHub!
