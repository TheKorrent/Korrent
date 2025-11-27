<script setup lang="ts">
  import { toast } from 'vue3-toastify'
  import { useApi } from '@/composables/Api.ts'
  import { useAuthStore } from '@/stores/auth.ts'

  const username = ref('')
  const password = ref('')

  const router = useRouter()
  const route = useRoute()
  const store = useAuthStore()

  const api = useApi()

  async function login () {
    try {
      const response = await api.login(username.value, password.value)

      const json = await response.json()

      if (json.code == 401) {
        toast.error('Invalid username or password')

        return
      }

      if (json.code == 200) {
        toast.success('Login successful')
        store.login()

        const redirect = route.query.redirect

        await (redirect && typeof redirect === 'string' ? router.push(redirect) : router.push('/'))
      }
    } catch (error) {
      toast.error('Unknown error')
      console.error(error)
    }
  }
</script>

<template>
  <v-card class="pa-4 mx-auto" max-width="400">
    <v-card-title class="text-h5 text-center">Login</v-card-title>

    <v-form>
      <v-text-field v-model="username" label="Username" prepend-inner-icon="mdi-account-outline" variant="outlined" />
      <v-text-field
        v-model="password"
        label="Password"
        prepend-inner-icon="mdi-lock-outline"
        type="password"
        variant="outlined"
      />

      <v-btn block color="primary" size="large" @click="login">Login</v-btn>
    </v-form>
  </v-card>
</template>

<style scoped>

</style>
