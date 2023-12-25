import request from '@/utils/request'

// 查询订单来源系统
export function listOrderSource() {
    return request({
      url: '/mast/material/listOrderSource',
      method: 'get',
    })
  }

  // 查询与月度订单
export function listMonthOrder() {
  return request({
    url: '/mast/material/listMonthOrder',
    method: 'get',
  })
}
// 查询与月度订单金额
export function listMonthOrderMoney() {
  return request({
    url: '/mast/material/listMonthOrderMoney',
    method: 'get',
  })
}
//查询待收货订单
export function treatNum() {
  return request({
    url: '/mast/material/selTreatNum',
    method: 'get',
  })
}
//查询待收货金额
export function treatMoney() {
  return request({
    url: '/mast/material/selTreatMoney',
    method: 'get',
  })
}
//查询已完成订单
export function stopNum() {
  return request({
    url: '/mast/material/selStopNum',
    method: 'get',
  })
}
//查询已完成金额
export function completeMoney() {
  return request({
    url: '/mast/material/selCompleteMoney',
    method: 'get',
  })
}
