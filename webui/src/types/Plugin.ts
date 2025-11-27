export interface Plugin {
  id: string
  version: string
  provider: string
  description: string
  license: string
  dependencies: string[]
  state: string
}
