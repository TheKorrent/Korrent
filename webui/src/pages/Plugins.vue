<script setup lang="ts">
  import type { Plugin } from '@/types/Plugin.ts'
  import type { ResponseData } from '@/types/ResponseData.ts'
  import { onMounted } from 'vue'
  import { useApi } from '@/composables/Api.ts'

  const plugins = ref<Plugin[]>([])
  const loading = ref(true)

  const api = useApi()

  async function getPlugins () {
    loading.value = true

    try {
      const response = await api.getPlugins()
      const json: ResponseData<Plugin[]> = await response.json()

      if (json.code == 200) {
        plugins.value = json.data
      }
    } catch (error) {
      console.error(error)
    } finally {
      loading.value = false
    }
  }

  onMounted(getPlugins)
</script>

<template>
  <v-card>
    <v-card-title>Plugins ({{ plugins.length }})</v-card-title>

    <v-expansion-panels variant="accordion">
      <v-expansion-panel
        v-for="plugin in plugins"
        :key="plugin.id"
      >
        <v-expansion-panel-title class="py-2">
          <div class="d-flex align-center w-100">
            <div class="flex-grow-1">
              <strong class="text-subtitle-1">{{ plugin.id }}</strong>
              <v-chip class="ml-2" color="teal" size="small" variant="outlined">v{{ plugin.version }}</v-chip>
            </div>

            <div class="text-caption text-medium-emphasis d-none d-sm-block">{{ plugin.provider }}</div>
          </div>
        </v-expansion-panel-title>

        <v-expansion-panel-text>
          <Plugin :plugin="plugin" />
        </v-expansion-panel-text>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>

<style scoped>

</style>
