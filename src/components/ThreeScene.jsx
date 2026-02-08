import React, { useRef, useMemo } from 'react';
import { useFrame } from '@react-three/fiber';
import { Sphere, MeshDistortMaterial, Float, Stars } from '@react-three/drei';
import * as THREE from 'three';

const AnimatedSphere = ({ position, color, speed = 1 }) => {
  const meshRef = useRef();

  useFrame((state) => {
    if (meshRef.current) {
      meshRef.current.rotation.x = state.clock.elapsedTime * speed * 0.2;
      meshRef.current.rotation.y = state.clock.elapsedTime * speed * 0.3;
    }
  });

  return (
    <Float speed={speed} rotationIntensity={0.5} floatIntensity={0.5}>
      <Sphere ref={meshRef} args={[1, 64, 64]} position={position}>
        <MeshDistortMaterial
          color={color}
          attach="material"
          distort={0.4}
          speed={2}
          roughness={0.1}
          metalness={0.8}
        />
      </Sphere>
    </Float>
  );
};

const ParticleField = () => {
  const particlesRef = useRef();
  
  const particlesCount = 1000;
  const positions = useMemo(() => {
    const positions = new Float32Array(particlesCount * 3);
    for (let i = 0; i < particlesCount; i++) {
      positions[i * 3] = (Math.random() - 0.5) * 20;
      positions[i * 3 + 1] = (Math.random() - 0.5) * 20;
      positions[i * 3 + 2] = (Math.random() - 0.5) * 20;
    }
    return positions;
  }, []);

  useFrame((state) => {
    if (particlesRef.current) {
      particlesRef.current.rotation.x = state.clock.elapsedTime * 0.05;
      particlesRef.current.rotation.y = state.clock.elapsedTime * 0.1;
    }
  });

  return (
    <points ref={particlesRef}>
      <bufferGeometry>
        <bufferAttribute
          attach="attributes-position"
          count={particlesCount}
          array={positions}
          itemSize={3}
        />
      </bufferGeometry>
      <pointsMaterial
        size={0.02}
        color="#0ea5e9"
        transparent
        opacity={0.6}
        sizeAttenuation
      />
    </points>
  );
};

const ThreeScene = () => {
  return (
    <>
      {/* Ambient lighting */}
      <ambientLight intensity={0.4} />
      
      {/* Directional light */}
      <directionalLight position={[10, 10, 5]} intensity={1} />
      
      {/* Point lights for dramatic effect */}
      <pointLight position={[-10, -10, -10]} color="#0ea5e9" intensity={0.5} />
      <pointLight position={[10, 10, 10]} color="#8b5cf6" intensity={0.5} />
      
      {/* Animated spheres */}
      <AnimatedSphere position={[-3, 2, -2]} color="#0ea5e9" speed={0.8} />
      <AnimatedSphere position={[3, -2, -1]} color="#8b5cf6" speed={1.2} />
      <AnimatedSphere position={[0, 0, -3]} color="#06b6d4" speed={1} />
      
      {/* Particle field */}
      <ParticleField />
      
      {/* Stars background */}
      <Stars
        radius={100}
        depth={50}
        count={5000}
        factor={4}
        saturation={0}
        fade
        speed={1}
      />
    </>
  );
};

export default ThreeScene;

