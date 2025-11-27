<script setup lang="ts">
  import { CodeEditor } from 'monaco-editor-vue3'
  import { toast } from 'vue3-toastify'
  import { useApi } from '@/composables/Api.ts'

  const props = defineProps<{
    id: string
    code: string
    isActive: boolean
  }>()

  const api = useApi()
  const config = ref(props.code)

  watch(() => props.code, newCode => {
    if (newCode !== undefined) {
      config.value = newCode
    }
  }, { immediate: true })

  const emit = defineEmits<{
    (event: 'update:isActive', value: boolean): void
  }>()

  const dialogActive = computed({
    get () {
      return props.isActive
    },
    set (value: boolean) {
      emit('update:isActive', value)
    },
  })

  function onCancel () {
    emit('update:isActive', false)
    config.value = props.code
  }

  async function updatePluginConfig () {
    const response = await api.updatePluginConfig(props.id, config.value)
    const json = await response.json()

    if (json.code == 200) {
      toast.success('Update plugin config successful')
      emit('update:isActive', false)
    } else {
      toast.error('Failed to update plugin config')
    }
  }

</script>

<template>
  <v-dialog v-model="dialogActive">
    <v-card>
      <v-card-title>Editor</v-card-title>

      <CodeEditor v-model:value="config" language="javascript" style="height: 800px" theme="vs-light" />

      <v-card-actions>
        <v-spacer /> <v-btn @click="onCancel">Cancel</v-btn>
        <v-btn color="primary" @click="updatePluginConfig">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
