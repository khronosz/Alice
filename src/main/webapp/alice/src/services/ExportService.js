import { Service } from "./Service";
import AuthService from "./AuthService";
import { API_URL } from "../components/App";

class ExportService extends Service {

    download = (url, fileName) => {
        fetch(API_URL + url, AuthService.header())
            .then(response => response.blob())
            .then(blob => {
                var url = window.URL.createObjectURL(blob);
                var a = document.createElement('a');
                a.href = url;
                a.download = fileName + "_" + this.getCurrentDate() + ".xlsx";
                document.body.appendChild(a);
                a.click();
                a.remove();
            })
            .catch(error => console.log(error))
    }

    getCurrentDate(separator = '') {
        let newDate = new Date()
        let date = newDate.getDate();
        let month = newDate.getMonth() + 1;
        let year = newDate.getFullYear();
        return `${year}${separator}${month < 10 ? `0${month}` : `${month}`}${separator}${date}`
    }
}

export default new ExportService();