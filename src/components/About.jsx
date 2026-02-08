import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { useInView } from 'react-intersection-observer';
import { Code2, Smartphone, Database, Globe, Zap, Heart, Camera, Mountain } from 'lucide-react';

const About = () => {
  const [ref, inView] = useInView({
    triggerOnce: true,
    threshold: 0.1,
  });

  const [activePhoto, setActivePhoto] = useState(0);

  const photos = [
    {
      src: "/src/assets/images/profile-cafe.jpg",
      alt: "Profile photo in cafe",
      caption: "Enjoying coffee and coding",
      icon: Camera
    },
    {
      src: "/src/assets/images/profile-mountain.jpg",
      alt: "Mountain adventure",
      caption: "Finding inspiration in nature",
      icon: Mountain
    }
  ];

  const skills = [
    {
      icon: Code2,
      name: 'PHP Development',
      description: 'Building robust server-side applications and APIs',
      level: 85,
      color: 'from-purple-500 to-purple-600',
      projects: '15+ Projects'
    },
    {
      icon: Smartphone,
      name: 'Android Studio',
      description: 'Creating native Android applications',
      level: 80,
      color: 'from-green-500 to-green-600',
      projects: '8+ Apps'
    },
    {
      icon: Globe,
      name: 'Web Technologies',
      description: 'Modern web development with React, JavaScript, and more',
      level: 90,
      color: 'from-blue-500 to-blue-600',
      projects: '20+ Websites'
    },
    {
      icon: Database,
      name: 'Database Design',
      description: 'MySQL, Firebase, and database optimization',
      level: 75,
      color: 'from-orange-500 to-orange-600',
      projects: '12+ Databases'
    }
  ];

  const interests = [
    { icon: Zap, text: 'Learning new technologies', color: 'text-yellow-400' },
    { icon: Code2, text: 'Open source contributions', color: 'text-green-400' },
    { icon: Heart, text: 'Problem solving', color: 'text-red-400' },
    { icon: Globe, text: 'Building user-friendly applications', color: 'text-blue-400' }
  ];

  return (
    <section className="section-padding bg-gradient-to-br from-dark-900 via-dark-800 to-dark-900 relative overflow-hidden" ref={ref}>
      {/* Animated background elements */}
      <div className="absolute inset-0 opacity-10">
        <div className="absolute top-20 left-10 w-72 h-72 bg-primary-500 rounded-full blur-3xl animate-pulse"></div>
        <div className="absolute bottom-20 right-10 w-96 h-96 bg-purple-500 rounded-full blur-3xl animate-pulse" style={{ animationDelay: '2s' }}></div>
      </div>

      <div className="container-custom relative z-10">
        <motion.div
          initial={{ opacity: 0, y: 50 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8 }}
          className="text-center mb-20"
        >
          <h2 className="text-5xl sm:text-6xl font-bold mb-6">
            About <span className="gradient-text">Me</span>
          </h2>
          <p className="text-xl text-gray-300 max-w-4xl mx-auto leading-relaxed">
            I'm a passionate developer who loves exploring new technologies and building 
            innovative solutions. Currently diving deep into PHP, Android development, 
            and modern web technologies while finding inspiration in both code and nature.
          </p>
        </motion.div>

        {/* Interactive Photo Gallery */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8, delay: 0.2 }}
          className="mb-20"
        >
          <div className="flex justify-center mb-8">
            <div className="flex space-x-4">
              {photos.map((photo, index) => (
                <motion.button
                  key={index}
                  onClick={() => setActivePhoto(index)}
                  className={`px-6 py-3 rounded-full transition-all duration-300 ${
                    activePhoto === index
                      ? 'bg-gradient-to-r from-primary-500 to-purple-600 text-white'
                      : 'bg-dark-700 text-gray-400 hover:bg-dark-600'
                  }`}
                  whileHover={{ scale: 1.05 }}
                  whileTap={{ scale: 0.95 }}
                >
                  <photo.icon className="w-5 h-5 inline mr-2" />
                  {photo.caption}
                </motion.button>
              ))}
            </div>
          </div>

          <div className="flex justify-center">
            <motion.div
              key={activePhoto}
              initial={{ opacity: 0, scale: 0.8 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ duration: 0.5 }}
              className="relative group"
            >
              <div className="w-80 h-80 rounded-3xl overflow-hidden bg-gradient-to-br from-primary-500 to-purple-600 p-1">
                <img
                  src={photos[activePhoto].src}
                  alt={photos[activePhoto].alt}
                  className="w-full h-full object-cover rounded-3xl group-hover:scale-110 transition-transform duration-500"
                />
              </div>
              <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent rounded-3xl opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
            </motion.div>
          </div>
        </motion.div>

        <div className="grid lg:grid-cols-2 gap-16 items-start mb-20">
          {/* Personal Info */}
          <motion.div
            initial={{ opacity: 0, x: -50 }}
            animate={inView ? { opacity: 1, x: 0 } : {}}
            transition={{ duration: 0.8, delay: 0.4 }}
            className="space-y-8"
          >
            <div className="text-center lg:text-left">
              <h3 className="text-3xl font-bold mb-4 gradient-text">Durafshaan Malik</h3>
              <p className="text-xl text-gray-300 mb-6">
                Full Stack Developer & Mobile App Enthusiast
              </p>
              <p className="text-gray-400 leading-relaxed">
                Passionate about creating digital experiences that make a difference. 
                I combine technical expertise with creative problem-solving to build 
                applications that users love. When I'm not coding, you'll find me 
                exploring new places and seeking inspiration in nature.
              </p>
            </div>
            
            <div className="grid grid-cols-2 gap-4">
              {interests.map((interest, index) => (
                <motion.div
                  key={index}
                  className="flex items-center space-x-3 p-4 rounded-xl bg-dark-700/50 backdrop-blur-sm border border-gray-700/50 hover:border-primary-500/50 transition-all duration-300"
                  initial={{ opacity: 0, y: 20 }}
                  animate={inView ? { opacity: 1, y: 0 } : {}}
                  transition={{ duration: 0.5, delay: 0.6 + index * 0.1 }}
                  whileHover={{ scale: 1.02, y: -2 }}
                >
                  <interest.icon className={`w-6 h-6 ${interest.color}`} />
                  <span className="text-gray-300 text-sm font-medium">{interest.text}</span>
                </motion.div>
              ))}
            </div>
          </motion.div>

          {/* Enhanced Skills */}
          <motion.div
            initial={{ opacity: 0, x: 50 }}
            animate={inView ? { opacity: 1, x: 0 } : {}}
            transition={{ duration: 0.8, delay: 0.6 }}
            className="space-y-8"
          >
            <div className="text-center lg:text-left">
              <h3 className="text-3xl font-bold mb-4 gradient-text">Skills & Technologies</h3>
              <p className="text-gray-400">Technologies I work with to bring ideas to life</p>
            </div>
            
            <div className="grid gap-6">
              {skills.map((skill, index) => (
                <motion.div
                  key={index}
                  className="group relative overflow-hidden rounded-2xl bg-gradient-to-br from-dark-700/80 to-dark-800/80 backdrop-blur-sm border border-gray-700/50 hover:border-primary-500/50 transition-all duration-500"
                  initial={{ opacity: 0, y: 30 }}
                  animate={inView ? { opacity: 1, y: 0 } : {}}
                  transition={{ duration: 0.6, delay: 0.8 + index * 0.1 }}
                  whileHover={{ scale: 1.02, y: -5 }}
                >
                  {/* Animated background gradient */}
                  <div className={`absolute inset-0 bg-gradient-to-r ${skill.color} opacity-0 group-hover:opacity-10 transition-opacity duration-500`}></div>
                  
                  <div className="relative p-6">
                    <div className="flex items-center justify-between mb-4">
                      <div className="flex items-center space-x-4">
                        <motion.div
                          className={`p-3 rounded-xl bg-gradient-to-r ${skill.color} shadow-lg`}
                          whileHover={{ rotate: 360 }}
                          transition={{ duration: 0.6 }}
                        >
                          <skill.icon className="w-7 h-7 text-white" />
                        </motion.div>
                        <div>
                          <h4 className="text-xl font-bold text-white group-hover:text-primary-300 transition-colors duration-300">
                            {skill.name}
                          </h4>
                          <p className="text-sm text-gray-400">{skill.projects}</p>
                        </div>
                      </div>
                      <div className="text-right">
                        <div className="text-2xl font-bold text-primary-400">{skill.level}%</div>
                        <div className="text-xs text-gray-500">Proficiency</div>
                      </div>
                    </div>
                    
                    <p className="text-gray-300 mb-4 leading-relaxed">{skill.description}</p>
                    
                    {/* Enhanced progress bar */}
                    <div className="relative">
                      <div className="w-full bg-dark-600 rounded-full h-3 overflow-hidden">
                        <motion.div
                          className={`h-full rounded-full bg-gradient-to-r ${skill.color} relative overflow-hidden`}
                          initial={{ width: 0 }}
                          animate={inView ? { width: `${skill.level}%` } : {}}
                          transition={{ duration: 1.5, delay: 1 + index * 0.2, ease: "easeOut" }}
                        >
                          {/* Animated shine effect */}
                          <motion.div
                            className="absolute inset-0 bg-gradient-to-r from-transparent via-white/30 to-transparent"
                            animate={{ x: [-100, 200] }}
                            transition={{ duration: 2, repeat: Infinity, delay: 2 + index * 0.3 }}
                          />
                        </motion.div>
                      </div>
                    </div>
                  </div>
                </motion.div>
              ))}
            </div>
          </motion.div>
        </div>

        {/* Enhanced Call to action */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8, delay: 1.2 }}
          className="text-center relative"
        >
          <div className="relative z-10 bg-gradient-to-br from-dark-800/80 to-dark-900/80 backdrop-blur-sm rounded-3xl p-12 border border-gray-700/50">
            <motion.div
              className="absolute inset-0 bg-gradient-to-r from-primary-500/10 to-purple-500/10 rounded-3xl"
              animate={{ opacity: [0.5, 1, 0.5] }}
              transition={{ duration: 3, repeat: Infinity }}
            />
            <div className="relative z-10">
              <h3 className="text-3xl font-bold mb-4 gradient-text">Ready to Create Something Amazing?</h3>
              <p className="text-xl text-gray-300 mb-8 max-w-2xl mx-auto leading-relaxed">
                I'm always excited to collaborate on interesting projects and learn from fellow developers. 
                Let's turn your ideas into reality!
              </p>
              <div className="flex flex-col sm:flex-row gap-4 justify-center">
                <motion.button
                  onClick={() => document.querySelector('#contact')?.scrollIntoView({ behavior: 'smooth' })}
                  className="px-8 py-4 bg-gradient-to-r from-primary-500 to-purple-600 text-white font-semibold rounded-xl hover:from-primary-600 hover:to-purple-700 transition-all duration-300 shadow-lg hover:shadow-primary-500/25"
                  whileHover={{ scale: 1.05, y: -2 }}
                  whileTap={{ scale: 0.95 }}
                >
                  Let's Work Together
                </motion.button>
                <motion.button
                  onClick={() => document.querySelector('#projects')?.scrollIntoView({ behavior: 'smooth' })}
                  className="px-8 py-4 bg-dark-700 text-white font-semibold rounded-xl hover:bg-dark-600 transition-all duration-300 border border-gray-600 hover:border-primary-500"
                  whileHover={{ scale: 1.05, y: -2 }}
                  whileTap={{ scale: 0.95 }}
                >
                  View My Work
                </motion.button>
              </div>
            </div>
          </div>
        </motion.div>
      </div>
    </section>
  );
};

export default About;
