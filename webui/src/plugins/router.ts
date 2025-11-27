import { setupLayouts } from 'virtual:generated-layouts'
import { toast } from 'vue3-toastify'
import { createRouter, createWebHistory } from 'vue-router'
import { useApi } from '@/composables/Api.ts'
import Login from '@/pages/Login.vue'
import Plugins from '@/pages/Plugins.vue'
import { useAuthStore } from '@/stores/auth.ts'

const routes = [
  {
    path: '/login',
    component: Login,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/',
    component: Plugins,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: '/plugins',
    component: Plugins,
    meta: {
      requiresAuth: true,
    },
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: setupLayouts(routes),
})

router.beforeEach(async (to, _, next) => {
  const store = useAuthStore()
  const api = useApi()

  if (store.isAuthenticated) {
    try {
      const response = await api.verify()
      const json = await response.json()

      if (json.code != 200) {
        toast.error('Invalid token')
        store.isAuthenticated = false
        next('/login')
      }
    } catch (error) {
      toast.error('Unknown error')
      console.error(error)
    }
  }

  if (store.isAuthenticated && to.path == '/login') {
    next('/')

    return
  }

  if (!store.isAuthenticated && to.meta.requiresAuth) {
    next({
      path: '/login',
      query: {
        redirect: to.fullPath,
      },
    })

    return
  }

  next()
})

export default router
