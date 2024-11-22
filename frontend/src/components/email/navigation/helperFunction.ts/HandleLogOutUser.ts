import axios from "axios";
import { NavigateFunction } from "react-router-dom";

export const HandleLogOutUser = async (navigate: NavigateFunction) => {
    try{
      const response = await axios.get("http://localdev:8082/api/users/logout", {
        withCredentials: true
      });
      if(response.status === 200){
        navigate('/');
    }
    }catch (error: unknown){
      if (axios.isAxiosError(error)) {
          console.log(error.message);
        }else if (error instanceof Error) {
          console.log(error.message);
        }
    }
  }