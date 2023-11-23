import request from '@/utils/request'

// 查询供应商详细列表
export function listSupplier(query) {
  return request({
    url: '/supplierpms/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商详细详细
export function getSupplier(sdId) {
  return request({
    url: '/supplierpms/supplier/' + sdId,
    method: 'get'
  })
}

// 新增供应商详细
export function addSupplier(data) {
  return request({
    url: '/supplierpms/supplier',
    method: 'post',
    data: data
  })
}

// 修改供应商详细
export function updateSupplier(data) {
  return request({
    url: '/supplierpms/supplier',
    method: 'put',
    data: data
  })
}

// 删除供应商详细
export function delSupplier(sdId) {
  return request({
    url: '/supplierpms/supplier/' + sdId,
    method: 'delete'
  })
}
