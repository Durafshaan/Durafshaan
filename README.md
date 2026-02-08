# Durafshaan's Dynamic Portfolio Website

A modern, interactive portfolio website built with React, Three.js, and Framer Motion. Features stunning 3D animations, smooth transitions, and a responsive design showcasing development skills.

## 🚀 Features

- **3D Interactive Elements**: Powered by Three.js and React Three Fiber
- **Smooth Animations**: Beautiful transitions using Framer Motion
- **Contact Form**: Email integration using mailto links
- **Responsive Design**: Mobile-first approach with Tailwind CSS
- **Modern Tech Stack**: React 18, Vite, and modern JavaScript
- **Performance Optimized**: Lightweight, fast-loading frontend-only architecture

## 🛠️ Technologies Used

- **Frontend**: React 18, Vite, Tailwind CSS
- **3D Graphics**: Three.js, React Three Fiber, React Three Drei
- **Animations**: Framer Motion
- **Icons**: Lucide React
- **Form Handling**: React Hook Form
- **Deployment**: Static hosting (Vercel, Netlify, GitHub Pages)

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Durafshaan/Durafshaan.git
   cd Durafshaan
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

## 🚀 Deployment

1. **Build the project**
   ```bash
   npm run build
   ```

2. **Deploy to static hosting**
   - **Vercel**: Connect your GitHub repo and deploy automatically
   - **Netlify**: Drag and drop the `dist` folder or connect via Git
   - **GitHub Pages**: Use GitHub Actions to deploy the `dist` folder

## 📁 Project Structure

```
src/
├── components/          # React components
│   ├── Header.jsx      # Navigation header
│   ├── Hero3D.jsx      # 3D hero section
│   ├── About.jsx       # About section
│   ├── Timeline.jsx    # Experience timeline
│   ├── Projects.jsx    # Projects showcase
│   ├── Contact.jsx     # Contact form
│   ├── ThreeScene.jsx  # 3D scene components
│   └── LoadingScreen.jsx

├── hooks/              # Custom React hooks
├── utils/              # Utility functions
├── assets/             # Static assets
│   ├── images/         # Image files
│   └── models/         # 3D models
└── styles/             # CSS styles
    └── global.css      # Global styles
```

## 🎨 Customization

### Adding Your Own Images
1. Place your images in `src/assets/images/`
2. Update the image references in components
3. Optimize images for web (WebP format recommended)

### Modifying Content
- **About Section**: Edit `src/components/About.jsx`
- **Projects**: Update the projects array in `src/components/Projects.jsx`
- **Timeline**: Modify timeline items in `src/components/Timeline.jsx`
- **Contact Info**: Update contact details in `src/components/Contact.jsx`

### Styling
- **Colors**: Modify the color palette in `tailwind.config.js`
- **Animations**: Customize animations in `src/styles/global.css`
- **3D Elements**: Adjust 3D scenes in `src/components/ThreeScene.jsx`

## 📱 Features Overview

### 3D Hero Section
- Interactive 3D spheres with distortion effects
- Particle systems and animated backgrounds
- Mouse-responsive camera controls

### Dynamic Projects Section
- Firebase-powered project data
- Filterable project categories
- Hover animations and smooth transitions

### Interactive Timeline
- Animated learning journey
- Skill progress indicators
- Achievement milestones

### Contact Form
- Firebase Firestore integration
- Form validation and error handling
- Success/error feedback animations

## 🔒 Privacy & Security

- No phone numbers or Instagram links included (as requested)
- Secure Firebase rules for data protection
- Contact form data stored securely in Firestore
- Visitor tracking for analytics (anonymous)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 📞 Contact

- **GitHub**: [github.com/Durafshaan](https://github.com/Durafshaan)
- **LinkedIn**: [linkedin.com/in/durafshaan](https://linkedin.com/in/durafshaan)
- **Email**: durafshaan@example.com

---

Built with ❤️ by Durafshaan Malik using modern web technologies.
