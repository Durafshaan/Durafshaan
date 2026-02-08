import { 
  collection, 
  addDoc, 
  getDocs, 
  doc, 
  updateDoc, 
  deleteDoc,
  query,
  orderBy,
  limit,
  where,
  serverTimestamp 
} from 'firebase/firestore';
import { db } from './config';

// Contact form submissions
export const submitContactForm = async (formData) => {
  try {
    const docRef = await addDoc(collection(db, 'contacts'), {
      ...formData,
      timestamp: serverTimestamp(),
      status: 'new'
    });
    return { success: true, id: docRef.id };
  } catch (error) {
    console.error('Error submitting contact form:', error);
    return { success: false, error: error.message };
  }
};

// Get all contact submissions (admin function)
export const getContactSubmissions = async () => {
  try {
    const q = query(
      collection(db, 'contacts'), 
      orderBy('timestamp', 'desc')
    );
    const querySnapshot = await getDocs(q);
    const contacts = [];
    querySnapshot.forEach((doc) => {
      contacts.push({ id: doc.id, ...doc.data() });
    });
    return { success: true, data: contacts };
  } catch (error) {
    console.error('Error getting contacts:', error);
    return { success: false, error: error.message };
  }
};

// Projects data management
export const addProject = async (projectData) => {
  try {
    const docRef = await addDoc(collection(db, 'projects'), {
      ...projectData,
      createdAt: serverTimestamp(),
      updatedAt: serverTimestamp()
    });
    return { success: true, id: docRef.id };
  } catch (error) {
    console.error('Error adding project:', error);
    return { success: false, error: error.message };
  }
};

export const getProjects = async () => {
  try {
    const q = query(
      collection(db, 'projects'), 
      orderBy('createdAt', 'desc')
    );
    const querySnapshot = await getDocs(q);
    const projects = [];
    querySnapshot.forEach((doc) => {
      projects.push({ id: doc.id, ...doc.data() });
    });
    return { success: true, data: projects };
  } catch (error) {
    console.error('Error getting projects:', error);
    return { success: false, error: error.message };
  }
};

// Skills and experience data
export const addSkill = async (skillData) => {
  try {
    const docRef = await addDoc(collection(db, 'skills'), {
      ...skillData,
      createdAt: serverTimestamp()
    });
    return { success: true, id: docRef.id };
  } catch (error) {
    console.error('Error adding skill:', error);
    return { success: false, error: error.message };
  }
};

export const getSkills = async () => {
  try {
    const q = query(
      collection(db, 'skills'), 
      orderBy('level', 'desc')
    );
    const querySnapshot = await getDocs(q);
    const skills = [];
    querySnapshot.forEach((doc) => {
      skills.push({ id: doc.id, ...doc.data() });
    });
    return { success: true, data: skills };
  } catch (error) {
    console.error('Error getting skills:', error);
    return { success: false, error: error.message };
  }
};

// Analytics and visitor tracking
export const trackVisitor = async (visitorData) => {
  try {
    await addDoc(collection(db, 'visitors'), {
      ...visitorData,
      timestamp: serverTimestamp()
    });
    return { success: true };
  } catch (error) {
    console.error('Error tracking visitor:', error);
    return { success: false, error: error.message };
  }
};

