import axios from "axios";

export const handleError = (error: unknown): string => {
    return axios.isAxiosError(error)
        ? `Axios error: ${error.message}`
        : error instanceof Error
        ? `General error: ${error.message}`
        : "Unknown error occurred";
};