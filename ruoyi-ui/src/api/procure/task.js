import request from '@/utils/request'

// 查询我的需求任务列表
export function listTask(query) {
  return request({
    url: '/procure/task/list',
    method: 'get',
    params: query
  })
}

// 查询我的需求任务详细
export function getTask(taskId) {
  return request({
    url: '/procure/task/' + taskId,
    method: 'get'
  })
}

// 新增我的需求任务
export function addTask(data) {
  return request({
    url: '/procure/task',
    method: 'post',
    data: data
  })
}

// 修改我的需求任务
export function updateTask(data) {
  return request({
    url: '/procure/task',
    method: 'put',
    data: data
  })
}

// 删除我的需求任务
export function delTask(taskId) {
  return request({
    url: '/procure/task/' + taskId,
    method: 'delete'
  })
}
