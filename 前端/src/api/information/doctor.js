import request from '@/utils/request'

const api_name = '/admin/info/doctorInfo'

export default {

  refreshInfo() {
    return request({
      url: `${api_name}/refresh`,
      method: 'GET'
    })
  },
  patientEnums(){
    return request({
      url: `${api_name}/anno/patient`,
      method: 'GET'
    })
  },
  infoDoctor(){
    return request({
      url: `${api_name}/info`,
      method: 'GET'
    })
  },
  infoDoctorById(id){
    return request({
      url: `${api_name}/info/${id}`,
      method: 'GET'
    })
  },
  saveDoctor(data){
    return request({
      url: `${api_name}/save`,
      method: 'POST',
      data: data
    })
  },
  updateDoctor(data) {
    return request({
      url: `${api_name}/update`,
      method: 'PUT',
      data: data,
    })
  },
  findList(data){
    return request({
      url: `${api_name}/list`,
      method: 'get',
      params: data
    })
  },
  listPatient(param){
    return request({
      url: `${api_name}/anno/list/patient`,
      method: 'get',
      params: param
    })
  },
  getAllDoctorInfo(){
    return request({
      url: `${api_name}/anno/all`,
      method: 'get'
    })
  }
}
