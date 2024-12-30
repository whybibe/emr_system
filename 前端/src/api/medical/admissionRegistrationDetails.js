import request from '@/utils/request'

const api_name = '/admin/medical/admission_registration_details'

export default {
  save(data) {
    return request({
      url: `${api_name}/add`,
      method: 'POST',
      data: data
    })
  },
  list(param) {
    return request({
      url: `${api_name}/list`,
      method: 'GET',
      params: param
    })
  },
  delete(param){
    return request({
      url: `${api_name}/delete`,
      method: 'delete',
      params: param
    })
  }
}
