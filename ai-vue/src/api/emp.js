import request from './request'

export function login(data) {
  return request.post('/login', data)
}

export function getEmpList() {
  return request.get('/emp/list')
}

export function getDeptList() {
  return request.get('/dept/list')
}

export function getEmpPage(params) {
  return request.get('/emp/page', { params })
}

export function getEmpById(id) {
  return request.get(`/emp/${id}`)
}

export function addEmp(data) {
  return request.post('/emp', data)
}

export function updateEmp(data) {
  return request.put('/emp', data)
}

export function deleteEmp(ids) {
  return request.delete('/emp', { params: { ids: ids.join(',') } })
}

export function changePassword(data) {
  return request.put('/emp/password', data)
}