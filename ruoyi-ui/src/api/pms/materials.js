import request from '@/utils/request'

// 查询订单物料明细列表
export function listMaterials(query) {
  return request({
    url: '/pms/materials/list',
    method: 'get',
    params: query
  })
}

// 查询订单物料明细详细
export function getMaterial(orId) {
  return request({
    url: '/pms/materials/' + orId,
    method: 'get'
  })
}

// 根据任务单号查询订单物料明细详细
export function findTaskMaterial(taskCode) {
  return request({
    url: '/pms/materials/findTaskMaterial/' + taskCode,
    method: 'get'
  })
}

// 新增订单物料明细
export function addMaterial(data) {
  return request({
    url: '/pms/materials',
    method: 'post',
    data: data
  })
}

// 修改订单物料明细
export function updateMaterial(data) {
  return request({
    url: '/pms/materials',
    method: 'put',
    data: data
  })
}

// 删除订单物料明细
export function delMaterial(orId) {
  return request({
    url: '/pms/materials/' + orId,
    method: 'delete'
  })
}
