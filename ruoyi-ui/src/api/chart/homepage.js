import request from '@/utils/request'

// 查询采购订单管理列表
export function listManager(query) {
    return request({
      url: '/pms/manager/list',
      method: 'get',
      params: query
    })
  }

  // 查询供应商列表
export function listDetails(query) {
    return request({
      url: '/supplierpms/details/list',
      method: 'get',
      params: query
    })
  }
 // 查询供应商分类定义列表
export function listClassification(query) {
    return request({
      url: '/supplierpms/classification/list',
      method: 'get',
      params: query
    })
  }
  //查询订单申请数
export function applyNum() {
  return request({
    url: '/mast/material/selApplyNum',
    method: 'get',
  })
}
  //查询订单申请数
  export function reconciliationRefuseNum() {
    return request({
      url: '/mast/material/selReconciliationRefuseNum',
      method: 'get',
    })
  }
    //查询订单拒绝申请数
export function reconciliationNum() {
  return request({
    url: '/mast/material/selReconciliationNum',
    method: 'get',
  })
}
//查询总订单数
export function orderTotal() {
  return request({
    url: '/mast/material/selOrderTotal',
    method: 'get',
  })
}
//查询订单总收货金额
export function orderTotalAmount() {
  return request({
    url: '/mast/material/selOrderTotalAmount',
    method: 'get',
  })
}
//查询总合同数
export function mentNum() {
  return request({
    url: '/mast/material/selMentNum',
    method: 'get',
  })
}
//查询合同未激活数
export function mentNumNo() {
  return request({
    url: '/mast/material/selMentNumNo',
    method: 'get',
  })
}
//查询质检总数
export function inspectTotal() {
  return request({
    url: '/mast/material/selInspectTotal',
    method: 'get',
  })
}
//查询质检未执行数
export function inspectTotalNo() {
  return request({
    url: '/mast/material/selInspectTotalNo',
    method: 'get',
  })
}