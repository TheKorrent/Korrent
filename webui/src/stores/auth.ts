import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
  const isAuthenticated = ref(false)

  function login () {
    isAuthenticated.value = true
  }

  return { isAuthenticated, login }
},
{
  persist: true,
},
)
