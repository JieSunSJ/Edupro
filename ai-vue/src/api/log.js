import request from './request'

export const getLogPage = (params) => request.get('/logs', { params })
