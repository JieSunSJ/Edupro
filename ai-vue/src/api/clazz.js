import request from './request'

export function getClazzPage(params) {
  return request.get('/clazzes', { params })
}

export function getClazzById(id) {
  return request.get(`/clazzes/${id}`)
}

export function getAllClazz() {
  return request.get('/clazzes/all')
}

export function getAllClazzPage(page, pageSize) {
  return request.get('/clazzes/all/page', { params: { page, pageSize } })
}

export function addClazz(data) {
  return request.post('/clazzes', data)
}

export function updateClazz(data) {
  return request.put('/clazzes', data)
}

export function deleteClazz(id) {
  return request.delete(`/clazzes/${id}`)
}