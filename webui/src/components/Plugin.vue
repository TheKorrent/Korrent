<script setup lang="ts">
  import type { Plugin } from '@/types/Plugin.ts'
  import { toast } from 'vue3-toastify'
  import { useApi } from '@/composables/Api.ts'

  const props = defineProps<{
    plugin: Plugin
  }>()

  const api = useApi()

  const config = ref()
  const isActive = ref()

  function formatDependencies (dependencies: string[]): string {
    return dependencies && dependencies.length > 0 ? dependencies.join(', ') : 'None'
  }

  async function removePlugin () {
    try {
      const response = await api.removePlugin(props.plugin.id)

      const json = await response.json()

      if (json.code == 204) {
        toast.success('Remove plugin successful')
      } else {
        console.error(json)
      }
    } catch (error) {
      console.error(error)
    }
  }

  async function disablePlugin () {
    try {
      const response = await api.disablePlugin(props.plugin.id)

      const json = await response.json()

      if (json.code == 204) {
        toast.success('Disable plugin successful')
      } else {
        console.error(json)
      }
    } catch (error) {
      console.error(error)
    }
  }

  async function enablePlugin () {
    try {
      const response = await api.enablePlugin(props.plugin.id)

      const json = await response.json()

      if (json.code == 204) {
        toast.success('Enable plugin successful')
      } else {
        console.error(json)
      }
    } catch (error) {
      console.error(error)
    }
  }

  async function getPluginConfig () {
    try {
      const response = await api.getPluginConfig(props.plugin.id)
      const json = await response.json()

      if (json.code == 200) {
        config.value = json.data
      }
    } catch (error) {
      console.error(error)
    }
  }

  async function handleEdit () {
    await getPluginConfig()
    if (config) {
      isActive.value = true
    }
  }
</script>

<template>
  <v-list class="py-0" density="compact">
    <v-list-item>
      <v-list-item-title>Version</v-list-item-title>
      <v-list-item-subtitle>{{ plugin.version }}</v-list-item-subtitle>
    </v-list-item>

    <v-list-item>
      <v-list-item-title>Provider</v-list-item-title>
      <v-list-item-subtitle>{{ plugin.provider }}</v-list-item-subtitle>
    </v-list-item>

    <v-list-item>
      <v-list-item-title>License</v-list-item-title>
      <v-list-item-subtitle>{{ plugin.license }}</v-list-item-subtitle>
    </v-list-item>

    <v-list-item>
      <v-list-item-title>Dependencies</v-list-item-title>
      <v-list-item-subtitle>{{ formatDependencies(plugin.dependencies) }}</v-list-item-subtitle>
    </v-list-item>

    <v-list-item>
      <v-list-item-title>State</v-list-item-title>
      <v-list-item-subtitle>{{ plugin.state }}</v-list-item-subtitle>
    </v-list-item>

    <v-divider class="my-2" />

    <v-list-item>
      <v-list-item-title class="text-subtitle-1">Description</v-list-item-title>
      <v-list-item-subtitle class="text-wrap mt-1">{{ plugin.description }}</v-list-item-subtitle>
    </v-list-item>

    <v-list-item-action>
      <v-btn-group>
        <v-btn v-if="plugin.state === 'DISABLED'" @click="enablePlugin">Enable</v-btn>
        <v-btn v-else @click="disablePlugin">Disable</v-btn>
        <v-btn @click="removePlugin">Remove</v-btn>
        <v-btn @click="handleEdit">Edit</v-btn>
      </v-btn-group>
    </v-list-item-action>

    <Editor :id="plugin.id" v-model:code="config" v-model:is-active="isActive" />
  </v-list>
</template>

<style scoped>

</style>
