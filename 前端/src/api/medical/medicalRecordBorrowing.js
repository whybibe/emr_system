import request from '@/utils/request'

const api_name = '/admin/medical/medical_record_borrowing'

export default {
  findAllList(param) {
    return request({
      url: `${api_name}/listAll`,
      method: 'GET',
      params: param
    })
  },
  findByUserId(param){
    return request({
      url: `${api_name}/listByUserId`,
      method: 'GET',
      params: param
    })
  },
  addMedicalRecord(data) {
    return request({
      url: `${api_name}/add`,
      method: 'POST',
      data: data
    })
  },
  returnMedicalRecord(id) {
    return request({
      url: `${api_name}/return/${id}`,
      method: 'POST'
    })
  },
  deleteMedicalRecord(id) {
    return request({
      url: `${api_name}/delete/${id}`,
      method: 'delete'
    })
  }
}
