import type { App } from 'vue'
import Vue3Toastify from 'vue3-toastify'
import pinia from './pinia.ts'
import router from './router.ts'
import toastify from './toastify.ts'
import vuetify from './vuetify'

export function registerPlugins (app: App) {
  app
    .use(vuetify)
    .use(pinia)
    .use(router)
    .use(Vue3Toastify, toastify)
}
