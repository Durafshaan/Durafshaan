import React from 'react';
import { motion } from 'framer-motion';
import { useInView } from 'react-intersection-observer';
import { Calendar, Code, Smartphone, Database, Globe, BookOpen, Trophy } from 'lucide-react';

const Timeline = () => {
  const [ref, inView] = useInView({
    triggerOnce: true,
    threshold: 0.1,
  });

  const timelineItems = [
    {
      year: '2024',
      title: 'Advanced Web Development',
      description: 'Diving deep into modern web technologies including React, Node.js, and Firebase integration.',
      icon: Globe,
      color: 'from-blue-500 to-blue-600',
      achievements: ['Built dynamic web applications', 'Learned Firebase integration', 'Mastered responsive design']
    },
    {
      year: '2023',
      title: 'Android Development Journey',
      description: 'Started learning Android Studio and mobile app development, creating my first native Android applications.',
      icon: Smartphone,
      color: 'from-green-500 to-green-600',
      achievements: ['First Android app published', 'Learned Java/Kotlin', 'UI/UX design principles']
    },
    {
      year: '2023',
      title: 'PHP & Backend Development',
      description: 'Began exploring server-side development with PHP, learning about databases and API development.',
      icon: Code,
      color: 'from-purple-500 to-purple-600',
      achievements: ['Built REST APIs', 'Database design', 'Server-side logic']
    },
    {
      year: '2022',
      title: 'Programming Foundation',
      description: 'Started my coding journey, learning fundamental programming concepts and problem-solving skills.',
      icon: BookOpen,
      color: 'from-orange-500 to-orange-600',
      achievements: ['Basic programming concepts', 'Problem-solving skills', 'Version control with Git']
    }
  ];

  return (
    <section className="section-padding" ref={ref}>
      <div className="container-custom">
        <motion.div
          initial={{ opacity: 0, y: 50 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8 }}
          className="text-center mb-16"
        >
          <h2 className="text-4xl sm:text-5xl font-bold mb-6">
            My <span className="gradient-text">Journey</span>
          </h2>
          <p className="text-xl text-gray-300 max-w-3xl mx-auto">
            A timeline of my learning journey and milestones in the world of programming and development.
          </p>
        </motion.div>

        <div className="relative">
          {/* Timeline line */}
          <div className="absolute left-1/2 transform -translate-x-1/2 w-1 h-full bg-gradient-to-b from-primary-500 to-purple-600 rounded-full"></div>

          <div className="space-y-12">
            {timelineItems.map((item, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 50 }}
                animate={inView ? { opacity: 1, y: 0 } : {}}
                transition={{ duration: 0.8, delay: index * 0.2 }}
                className={`flex items-center ${
                  index % 2 === 0 ? 'flex-row' : 'flex-row-reverse'
                }`}
              >
                {/* Content */}
                <div className={`w-5/12 ${index % 2 === 0 ? 'pr-8 text-right' : 'pl-8 text-left'}`}>
                  <motion.div
                    className="glass-effect p-6 rounded-xl"
                    whileHover={{ scale: 1.02, y: -5 }}
                    transition={{ duration: 0.3 }}
                  >
                    <div className={`flex items-center mb-4 ${
                      index % 2 === 0 ? 'justify-end' : 'justify-start'
                    }`}>
                      <Calendar className="w-5 h-5 text-primary-400 mr-2" />
                      <span className="text-primary-400 font-semibold text-lg">{item.year}</span>
                    </div>
                    
                    <h3 className="text-xl font-bold mb-3">{item.title}</h3>
                    <p className="text-gray-300 mb-4">{item.description}</p>
                    
                    <div className="space-y-2">
                      {item.achievements.map((achievement, achievementIndex) => (
                        <motion.div
                          key={achievementIndex}
                          className={`flex items-center ${
                            index % 2 === 0 ? 'justify-end' : 'justify-start'
                          }`}
                          initial={{ opacity: 0, x: index % 2 === 0 ? 20 : -20 }}
                          animate={inView ? { opacity: 1, x: 0 } : {}}
                          transition={{ duration: 0.5, delay: (index * 0.2) + (achievementIndex * 0.1) + 0.5 }}
                        >
                          <Trophy className="w-4 h-4 text-yellow-400 mr-2" />
                          <span className="text-sm text-gray-400">{achievement}</span>
                        </motion.div>
                      ))}
                    </div>
                  </motion.div>
                </div>

                {/* Timeline dot */}
                <div className="relative z-10">
                  <motion.div
                    className={`w-16 h-16 rounded-full bg-gradient-to-r ${item.color} flex items-center justify-center shadow-lg`}
                    initial={{ scale: 0 }}
                    animate={inView ? { scale: 1 } : {}}
                    transition={{ duration: 0.5, delay: index * 0.2 + 0.3 }}
                    whileHover={{ scale: 1.1 }}
                  >
                    <item.icon className="w-8 h-8 text-white" />
                  </motion.div>
                  
                  {/* Glow effect */}
                  <motion.div
                    className={`absolute inset-0 rounded-full bg-gradient-to-r ${item.color} opacity-30 blur-lg`}
                    animate={{ scale: [1, 1.2, 1] }}
                    transition={{ duration: 2, repeat: Infinity }}
                  />
                </div>

                {/* Empty space for opposite side */}
                <div className="w-5/12"></div>
              </motion.div>
            ))}
          </div>
        </div>

        {/* Current status */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={inView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.8, delay: 1 }}
          className="text-center mt-16"
        >
          <div className="glass-effect p-8 rounded-xl max-w-2xl mx-auto">
            <h3 className="text-2xl font-bold mb-4">
              Currently <span className="gradient-text">Learning</span>
            </h3>
            <p className="text-gray-300 mb-6">
              I'm continuously expanding my skills and exploring new technologies. 
              Always excited to take on new challenges and collaborate on innovative projects.
            </p>
            
            <div className="flex flex-wrap justify-center gap-3">
              {['React', 'Firebase', 'Three.js', 'Node.js', 'MongoDB', 'TypeScript'].map((tech, index) => (
                <motion.span
                  key={index}
                  className="px-4 py-2 bg-primary-500/20 text-primary-300 rounded-full text-sm font-medium"
                  initial={{ opacity: 0, scale: 0.8 }}
                  animate={inView ? { opacity: 1, scale: 1 } : {}}
                  transition={{ duration: 0.3, delay: 1.2 + index * 0.1 }}
                  whileHover={{ scale: 1.05 }}
                >
                  {tech}
                </motion.span>
              ))}
            </div>
          </div>
        </motion.div>
      </div>
    </section>
  );
};

export default Timeline;

