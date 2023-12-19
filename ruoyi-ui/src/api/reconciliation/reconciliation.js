import request from '@/utils/request'

// 查询对账单列表
export function listReconciliation(query) {
  return request({
    url: '/mast/reconciliation/list',
    method: 'get',
    params: query
  })
}

//外键查询 采购单号------------------------------------------
export function listOrderDetail(query) {
  return request({
    url: '/pms/orderDetail/list',
    method: 'get',
    params: query
  })
}

// 查询对账单详细
export function getReconciliation(statementAccountId) {
  return request({
    url: '/mast/reconciliation/' + statementAccountId,
    method: 'get'
  })
}

// 新增对账单
export function addReconciliation(data) {
  return request({
    url: '/mast/reconciliation',
    method: 'post',
    data: data
  })
}

// 修改对账单
export function updateReconciliation(data) {
  return request({
    url: '/mast/reconciliation',
    method: 'put',
    data: data
  })
}

// 删除对账单
export function delReconciliation(statementAccountId) {
  return request({
    url: '/mast/reconciliation/' + statementAccountId,
    method: 'delete'
  })
}
