import axios from "axios";

interface WritePostRequest {
  title: string;
  content: string;
}

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export const uploadPost = async (
  postData: WritePostRequest,
  imageFile?: File
) => {
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(postData)], {
    type: "application/json",
  });
  formData.append("text", jsonBlob, "text.json");

  if (imageFile) {
    formData.append("imageFile", imageFile);
  }

  try {
    const response = await api.post("/posts", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw new Error(error.response?.data?.message || "게시물 업로드 실패");
    }
    throw error;
  }
};
