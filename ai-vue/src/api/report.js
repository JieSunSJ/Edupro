import request from './request'

export function getEmpJobData() {
  return request.get('/report/empJobData')
}

export function getEmpGenderData() {
  return request.get('/report/empGenderData')
}

export function getStudentClazzData() {
  return request.get('/report/studentCountData')
}