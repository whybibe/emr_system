import request from '@/utils/request'

const api_name = '/admin/medical/admission_registration'

export default {
  findList(param) {
    return request({
      url: `${api_name}/list`,
      method: 'GET',
      params: param
    })
  },
  // 保存电子病例
  saveAdmissionRegistration(data){
    return request({
      url: `${api_name}/save`,
      method: 'POST',
      data: data
    })
  },
  // 删除电子病例
  deleteByIds(ids) {
    return request({
      url: `${api_name}/delete`,
      method: 'delete',
      data: ids
    })
  },
  listNotOut(param){
    return request({
      url: `${api_name}/listNotOut`,
      method: 'get',
      params: param
    })
  },
  updateData(data) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: data
    })
  },
  getAdmissionRegistrationByCaseType(param){
    return request({
      url: `${api_name}/getAdmissionRegistrationByCaseType`,
      method: 'get',
      params: param
    })
  },
  getById(id){
    return request({
      url: `${api_name}/getById/${id}`,
      method: 'GET'
    })
  }
}
