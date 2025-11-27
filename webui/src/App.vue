<script setup lang="ts">
  const drawer = ref(false)
</script>

<template>
  <v-app>
    <v-app-bar>
      <v-app-bar-nav-icon variant="text" @click.stop="drawer = !drawer" />
      <v-app-bar-title>Korrent</v-app-bar-title>

      <template #append>
        <a href="https://github.com/TheKorrent/Korrent" rel="noopener noreferrer" target="_blank">
          <v-btn color="black" icon="mdi-github" />
        </a>
      </template>
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" permanent>
      <v-list nav>
        <v-list-item link title="Plugins" to="/plugins" />
      </v-list>
    </v-navigation-drawer>

    <v-main class="align-center justify-center">
      <v-container class="fill-height">
        <v-row align="center" class="fill-height">
          <v-col
            class="mx-auto"
            cols="12"
            lg="4"
            md="6"
            sm="8"
          >
            <RouterView />
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>

</style>
