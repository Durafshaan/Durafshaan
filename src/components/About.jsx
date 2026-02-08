import React from 'react';
import { motion } from 'framer-motion';
import { useInView } from 'react-intersection-observer';
import { Code2, Smartphone, Database, Globe, Zap, Heart } from 'lucide-react';

const About = () => {
  const [ref, inView] = useInView({
    triggerOnce: true,
    threshold: 0.1,
  });

  const skills = [
    {
      icon: Code2,
      name: 'PHP Development',
      description: 'Building robust server-side applications and APIs',
      level: 75,
      color: 'from-purple-500 to-purple-600'
    },
    {
      icon: Smartphone,
      name: 'Android Studio',
      description: 'Creating native Android applications',
      level: 70,
      color: 'from-green-500 to-green-600'
    },
    {
      icon: Globe,
      name: 'Web Technologies',
      description: 'Modern web development with React, JavaScript, and more',
      level: 80,
      color: 'from-blue-500 to-blue-600'
    },
    {
      icon: Database,
      name: 'Database Design',
      description: 'MySQL, Firebase, and database optimization',
      level: 65,
      color: 'from-orange-500 to-orange-600'
    }
  ];

  const interests = [
    { icon: Zap, text: 'Learning new technologies' },
    { icon: Code2, text: 'Open source contributions' },
    { icon: Heart, text: 'Problem solving' },
    { icon: Globe, text: 'Building user-friendly applications' }
  ];

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
            About <span className="gradient-text">Me</span>
          </h2>
          <p className="text-xl text-gray-300 max-w-3xl mx-auto">
            I'm a passionate developer who loves exploring new technologies and building 
            innovative solutions. Currently diving deep into PHP, Android development, 
            and modern web technologies.
          </p>
        </motion.div>

        <div className="grid lg:grid-cols-2 gap-12 items-center mb-16">
          {/* Profile Image and Info */}
          <motion.div
            initial={{ opacity: 0, x: -50 }}
            animate={inView ? { opacity: 1, x: 0 } : {}}
            transition={{ duration: 0.8, delay: 0.2 }}
            className="text-center lg:text-left"
          >
            <div className="relative inline-block mb-8">
              <motion.div
                className="w-64 h-64 mx-auto lg:mx-0 rounded-full bg-gradient-to-br from-primary-500 to-purple-600 p-1"
                whileHover={{ scale: 1.05 }}
                transition={{ duration: 0.3 }}
              >
                <div className="w-full h-full rounded-full bg-dark-800 flex items-center justify-center">
                  <div className="w-48 h-48 rounded-full bg-gradient-to-br from-primary-400 to-purple-500 flex items-center justify-center text-6xl font-bold text-white">
                    D
                  </div>
                </div>
              </motion.div>
              
              {/* Floating icons around profile */}
              <motion.div
                className="absolute -top-4 -right-4 w-12 h-12 bg-primary-500 rounded-full flex items-center justify-center"
                animate={{ rotate: 360 }}
                transition={{ duration: 20, repeat: Infinity, ease: 'linear' }}
              >
                <Code2 className="w-6 h-6 text-white" />
              </motion.div>
              <motion.div
                className="absolute -bottom-4 -left-4 w-12 h-12 bg-purple-500 rounded-full flex items-center justify-center"
                animate={{ rotate: -360 }}
                transition={{ duration: 15, repeat: Infinity, ease: 'linear' }}
              >
                <Smartphone className="w-6 h-6 text-white" />
              </motion.div>
            </div>

            <h3 className="text-2xl font-bold mb-4">Durafshaan Malik</h3>
            <p className="text-gray-300 text-lg mb-6">
              Full Stack Developer & Mobile App Enthusiast
            </p>
            
            <div className="space-y-3">
              {interests.map((interest, index) => (
                <motion.div
                  key={index}
                  className="flex items-center justify-center lg:justify-start space-x-3"
                  initial={{ opacity: 0, x: -20 }}
                  animate={inView ? { opacity: 1, x: 0 } : {}}
                  transition={{ duration: 0.5, delay: 0.4 + index * 0.1 }}
                >
                  <interest.icon className="w-5 h-5 text-primary-400" />
                  <span className="text-gray-300">{interest.text}</span>
                </motion.div>
              ))}
            </div>
          </motion.div>

          {/* Skills */}
          <motion.div
            initial={{ opacity: 0, x: 50 }}
            animate={inView ? { opacity: 1, x: 0 } : {}}
            transition={{ duration: 0.8, delay: 0.4 }}
            className="space-y-6"
          >
            <h3 className="text-2xl font-bold mb-8 text-center lg:text-left">
              Skills & Technologies
            </h3>
            
            {skills.map((skill, index) => (
              <motion.div
                key={index}
                className="glass-effect p-6 rounded-xl"
                initial={{ opacity: 0, y: 20 }}
                animate={inView ? { opacity: 1, y: 0 } : {}}
                transition={{ duration: 0.5, delay: 0.6 + index * 0.1 }}
                whileHover={{ scale: 1.02, y: -5 }}
              >
                <div className="flex items-start space-x-4">
                  <div className={`p-3 rounded-lg bg-gradient-to-r ${skill.color}`}>
                    <skill.icon className="w-6 h-6 text-white" />
                  </div>
                  <div className="flex-1">
                    <h4 className="text-lg font-semibold mb-2">{skill.name}</h4>
                    <p className="text-gray-400 text-sm mb-3">{skill.description}</p>
                    
                    {/* Progress bar */}
                    <div className="w-full bg-dark-700 rounded-full h-2">
                      <motion.div
                        className={`h-2 rounded-full bg-gradient-to-r ${skill.color}`}
                        initial={{ width: 0 }}
                        animate={inView ? { width: `${skill.level}%` } : {}}
                        transition={{ duration: 1, delay: 0.8 + index * 0.2 }}
                      />
                    </div>
                    <div className="text-right text-sm text-gray-400 mt-1">
                      {skill.level}%
                    </div>
                  </div>
                </div>
              </motion.div>
            ))}
          </motion.div>
        </div>

        {/* Call to action */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8, delay: 1 }}
          className="text-center"
        >
          <p className="text-lg text-gray-300 mb-6">
            I'm always excited to collaborate on interesting projects and learn from fellow developers.
          </p>
          <motion.button
            onClick={() => document.querySelector('#contact')?.scrollIntoView({ behavior: 'smooth' })}
            className="btn-primary"
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            Let's Work Together
          </motion.button>
        </motion.div>
      </div>
    </section>
  );
};

export default About;

