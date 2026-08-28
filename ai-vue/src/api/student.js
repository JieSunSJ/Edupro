import request from './request'

export function login(data) {
  return request.post('/student/login', data)
}

export function getStudentPage(params) {
  return request.get("/students/list", { params })
}

export function getStudentById(id) {
  return request.get("/students/" + id)
}

export function addStudent(data) {
  return request.post("/students/add", data)
}

export function updateStudent(data) {
  return request.put("/students/update", data)
}

export function deleteStudent(id) {
  return request.delete("/students/delete/" + id)
}

export function getStudentProfile() {
  return request.get("/student/profile")
}

export function updateStudentProfile(data) {
  return request.put("/student/profile", data)
}

export function changeStudentPassword(data) {
  return request.put("/student/password", data)
}

