import request from './request'

export const login = (data) => request.post('/user/login', data)

export const getProfile = () => request.get('/user/profile')

export const updateProfile = (data) => request.put('/user/profile', data)

export const changePassword = (data) => request.put('/user/password', data)

export const updateImage = (data) => request.put('/user/image', data)

export const getTeachers = () => request.get('/user/teachers')
