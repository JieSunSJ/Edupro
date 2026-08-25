import request from './request'

export function getLogList() {
  return request.get('/logs/list')
}

export function getLogPage(page, pageSize) {
  return request.get('/logs/page', { params: { page, pageSize } })
}

export function deleteLog(id) {
  return request.delete(`/logs/${id}`)
}