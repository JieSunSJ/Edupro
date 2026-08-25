import request from './request'

export function getStudentPage(params) {
  return request.get('/students/list', { params })
}

export function getStudentById(id) {
  return request.get(`/students/${id}`)
}

export function addStudent(data) {
  return request.post('/students/add', data)
}

export function updateStudent(data) {
  return request.put('/students/update', data)
}

export function deleteStudent(id) {
  return request.delete(`/students/delete/${id}`)
}