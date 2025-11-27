import { toast, type ToastContainerOptions } from 'vue3-toastify'
import 'vue3-toastify/dist/index.css'

export default {
  autoClose: 3500,
  clearOnUrlChange: false,
  containerId: toast.POSITION.BOTTOM_RIGHT,
  limit: 5,
  position: toast.POSITION.BOTTOM_RIGHT,
  theme: toast.THEME.COLORED,
} as ToastContainerOptions
