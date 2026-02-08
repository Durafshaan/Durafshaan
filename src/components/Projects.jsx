import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { useInView } from 'react-intersection-observer';
import { ExternalLink, Github, Filter, Smartphone, Globe, Database, Code } from 'lucide-react';

const Projects = () => {
  const [ref, inView] = useInView({
    triggerOnce: true,
    threshold: 0.1,
  });

  const [filteredProjects, setFilteredProjects] = useState([]);
  const [activeFilter, setActiveFilter] = useState('all');

  // Static projects data
  const projects = [
    {
      id: 1,
      title: 'E-Commerce Web App',
      description: 'A full-stack e-commerce application built with PHP and MySQL, featuring user authentication, product catalog, and payment integration.',
      image: '/api/placeholder/400/250',
      technologies: ['PHP', 'MySQL', 'JavaScript', 'Bootstrap'],
      category: 'web',
      github: 'https://github.com/Durafshaan/ecommerce-app',
      demo: 'https://demo-ecommerce.com',
      featured: true
    },
    {
      id: 2,
      title: 'Task Manager Android App',
      description: 'A native Android application for task management with offline support, built using Android Studio and SQLite database.',
      image: '/api/placeholder/400/250',
      technologies: ['Java', 'Android Studio', 'SQLite', 'Material Design'],
      category: 'mobile',
      github: 'https://github.com/Durafshaan/task-manager-android',
      demo: null,
      featured: true
    },
    {
      id: 3,
      title: 'Weather Dashboard',
      description: 'A responsive weather dashboard that displays current weather and forecasts using external APIs and modern web technologies.',
      image: '/api/placeholder/400/250',
      technologies: ['React', 'API Integration', 'CSS3', 'Chart.js'],
      category: 'web',
      github: 'https://github.com/Durafshaan/weather-dashboard',
      demo: 'https://weather-dashboard-demo.com',
      featured: false
    },
    {
      id: 4,
      title: 'Student Management System',
      description: 'A comprehensive student management system with CRUD operations, built with PHP and featuring a clean admin interface.',
      image: '/api/placeholder/400/250',
      technologies: ['PHP', 'MySQL', 'HTML5', 'CSS3'],
      category: 'web',
      github: 'https://github.com/Durafshaan/student-management',
      demo: null,
      featured: false
    },
    {
      id: 5,
      title: 'Fitness Tracker Mobile App',
      description: 'An Android fitness tracking application with workout logging, progress tracking, and motivational features.',
      image: '/api/placeholder/400/250',
      technologies: ['Kotlin', 'Android Studio', 'Room Database', 'Charts'],
      category: 'mobile',
      github: 'https://github.com/Durafshaan/fitness-tracker',
      demo: null,
      featured: false
    },
    {
      id: 6,
      title: 'Portfolio Website',
      description: 'This very website! A dynamic portfolio with 3D elements, animations, and modern web technologies.',
      image: '/api/placeholder/400/250',
      technologies: ['React', 'Three.js', 'Framer Motion', 'Tailwind CSS'],
      category: 'web',
      github: 'https://github.com/Durafshaan/portfolio',
      demo: 'https://durafshaan.dev',
      featured: true
    }
  ];

  const filters = [
    { id: 'all', label: 'All Projects', icon: Filter },
    { id: 'web', label: 'Web Apps', icon: Globe },
    { id: 'mobile', label: 'Mobile Apps', icon: Smartphone },
    { id: 'backend', label: 'Backend', icon: Database }
  ];

  useEffect(() => {
    // Initialize with all projects
    setFilteredProjects(projects);
  }, []);

  const handleFilterChange = (filterId) => {
    setActiveFilter(filterId);
    if (filterId === 'all') {
      setFilteredProjects(projects);
    } else {
      setFilteredProjects(projects.filter(project => project.category === filterId));
    }
  };

  const ProjectCard = ({ project, index }) => (
    <motion.div
      layout
      initial={{ opacity: 0, y: 50 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: 50 }}
      transition={{ duration: 0.5, delay: index * 0.1 }}
      className="glass-effect rounded-xl overflow-hidden group hover:shadow-2xl transition-all duration-300"
      whileHover={{ y: -10 }}
    >
      {/* Project Image */}
      <div className="relative h-48 bg-gradient-to-br from-primary-500/20 to-purple-500/20 overflow-hidden">
        <div className="absolute inset-0 flex items-center justify-center">
          <Code className="w-16 h-16 text-primary-400/50" />
        </div>
        {project.featured && (
          <div className="absolute top-4 right-4 bg-yellow-500 text-black px-2 py-1 rounded-full text-xs font-bold">
            Featured
          </div>
        )}
        <div className="absolute inset-0 bg-gradient-to-t from-dark-900/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300" />
      </div>

      {/* Project Content */}
      <div className="p-6">
        <h3 className="text-xl font-bold mb-3 group-hover:text-primary-400 transition-colors duration-300">
          {project.title}
        </h3>
        <p className="text-gray-300 mb-4 text-sm leading-relaxed">
          {project.description}
        </p>

        {/* Technologies */}
        <div className="flex flex-wrap gap-2 mb-4">
          {project.technologies.map((tech, techIndex) => (
            <span
              key={techIndex}
              className="px-2 py-1 bg-primary-500/20 text-primary-300 rounded text-xs font-medium"
            >
              {tech}
            </span>
          ))}
        </div>

        {/* Action Buttons */}
        <div className="flex space-x-3">
          {project.github && (
            <motion.a
              href={project.github}
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center space-x-2 text-gray-400 hover:text-primary-400 transition-colors duration-300"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              <Github className="w-4 h-4" />
              <span className="text-sm">Code</span>
            </motion.a>
          )}
          {project.demo && (
            <motion.a
              href={project.demo}
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center space-x-2 text-gray-400 hover:text-primary-400 transition-colors duration-300"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              <ExternalLink className="w-4 h-4" />
              <span className="text-sm">Demo</span>
            </motion.a>
          )}
        </div>
      </div>
    </motion.div>
  );

  return (
    <section className="section-padding bg-dark-800/50" ref={ref}>
      <div className="container-custom">
        <motion.div
          initial={{ opacity: 0, y: 50 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8 }}
          className="text-center mb-16"
        >
          <h2 className="text-4xl sm:text-5xl font-bold mb-6">
            My <span className="gradient-text">Projects</span>
          </h2>
          <p className="text-xl text-gray-300 max-w-3xl mx-auto">
            A showcase of my work in web development, mobile applications, and various programming projects.
          </p>
        </motion.div>

        {/* Filter Buttons */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8, delay: 0.2 }}
          className="flex flex-wrap justify-center gap-4 mb-12"
        >
          {filters.map((filter, index) => (
            <motion.button
              key={filter.id}
              onClick={() => handleFilterChange(filter.id)}
              className={`flex items-center space-x-2 px-6 py-3 rounded-lg font-medium transition-all duration-300 ${
                activeFilter === filter.id
                  ? 'bg-primary-500 text-white shadow-lg'
                  : 'glass-effect text-gray-300 hover:text-primary-400'
              }`}
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: index * 0.1 }}
            >
              <filter.icon className="w-4 h-4" />
              <span>{filter.label}</span>
            </motion.button>
          ))}
        </motion.div>

        {/* Projects Grid */}
        <AnimatePresence mode="wait">
          <motion.div
            key={activeFilter}
            className="grid md:grid-cols-2 lg:grid-cols-3 gap-8"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.3 }}
          >
            {filteredProjects.map((project, index) => (
              <ProjectCard key={project.id} project={project} index={index} />
            ))}
          </motion.div>
        </AnimatePresence>

        {filteredProjects.length === 0 && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-center py-12"
          >
            <p className="text-gray-400 text-lg">No projects found for this category.</p>
          </motion.div>
        )}

        {/* Call to Action */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8, delay: 0.6 }}
          className="text-center mt-16"
        >
          <p className="text-lg text-gray-300 mb-6">
            Interested in collaborating or want to see more of my work?
          </p>
          <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
            <motion.a
              href="https://github.com/Durafshaan"
              target="_blank"
              rel="noopener noreferrer"
              className="btn-primary"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              View All on GitHub
            </motion.a>
            <motion.button
              onClick={() => document.querySelector('#contact')?.scrollIntoView({ behavior: 'smooth' })}
              className="btn-secondary"
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              Get In Touch
            </motion.button>
          </div>
        </motion.div>
      </div>
    </section>
  );
};

export default Projects;
