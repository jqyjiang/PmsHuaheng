import request from '@/utils/request'

// 查询现场考察列表
export function listInspection(query) {
  return request({
    url: '/supplierpms/inspection/list',
    method: 'get',
    params: query
  })
}

// 查询现场考察详细
export function getInspection(soiId) {
  return request({
    url: '/supplierpms/inspection/' + soiId,
    method: 'get'
  })
}

// 新增现场考察
export function addInspection(data) {
  return request({
    url: '/supplierpms/inspection',
    method: 'post',
    data: data
  })
}

// 修改现场考察
export function updateInspection(data) {
  return request({
    url: '/supplierpms/inspection',
    method: 'put',
    data: data
  })
}

// 删除现场考察
export function delInspection(soiId) {
  return request({
    url: '/supplierpms/inspection/' + soiId,
    method: 'delete'
  })
}
