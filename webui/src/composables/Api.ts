export function useApi () {
  return {
    login,
    verify,
    getPlugins,
    removePlugin,
    disablePlugin,
    enablePlugin,
    getPluginConfig,
    updatePluginConfig,
  }
}

async function login (username: string, password: string) {
  const data = new FormData()

  data.append('username', username)
  data.append('password', password)

  return await fetch('/api/v0/auth/login', {
    method: 'POST',
    credentials: 'same-origin',
    body: data,
  })
}
async function verify () {
  return await fetch('/api/v0/auth/verify', {
    method: 'GET',
    credentials: 'same-origin',
  })
}

async function getPlugins () {
  return await fetch('/api/v0/plugins', {
    method: 'GET',
    credentials: 'same-origin',
  })
}

async function removePlugin (id: string) {
  return await fetch(`/api/v0/plugins/${id}`, {
    method: 'DELETE',
    credentials: 'same-origin',
  })
}

async function disablePlugin (id: string) {
  return await fetch(`/api/v0/plugins/${id}/disable`, {
    method: 'PATCH',
    credentials: 'same-origin',
  })
}

async function enablePlugin (id: string) {
  return await fetch(`/api/v0/plugins/${id}/enable`, {
    method: 'PATCH',
    credentials: 'same-origin',
  })
}

async function getPluginConfig (id: string) {
  return await fetch(`/api/v0/plugins/${id}/config`, {
    method: 'GET',
    credentials: 'same-origin',
  })
}

async function updatePluginConfig (id: string, config: string) {
  return await fetch(`/api/v0/plugins/${id}/config`, {
    method: 'PUT',
    credentials: 'same-origin',
    body: config,
  })
}
