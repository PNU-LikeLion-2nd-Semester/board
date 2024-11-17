import axios from "axios";
import {uploadPost} from "../posts";

jest.mock("axios");
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe("uploadPost", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    mockedAxios.create.mockReturnValue(mockedAxios);
  });

  it("should upload post with image", async () => {
    const mockPostData = {
      title: "Test Title with Image",
      content: "Test Content with Image",
    };
    const mockFile = new File([""], "test.jpg", {type: "image/jpeg"});
    const mockResponse = {
      data: {id: "2", ...mockPostData, imageUrl: "http://example.com/test.jpg"},
    };

    mockedAxios.post.mockResolvedValue(mockResponse);

    const result = await uploadPost(mockPostData, mockFile);

    expect(mockedAxios.post).toHaveBeenCalledWith(
      "/api/posts",
      expect.any(FormData),
      expect.objectContaining({
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
    );

    expect(result).toEqual(mockResponse.data);
  });

  it("should throw an error when the request fails", async () => {
    const mockPostData = {
      title: "Failed Post",
      content: "This post will fail",
    };

    const mockError = {
      response: {
        data: {
          message: "Server error",
        },
      },
    };
    mockedAxios.post.mockRejectedValue(mockError);
    mockedAxios.isAxiosError.mockReturnValue(true);

    await expect(uploadPost(mockPostData)).rejects.toThrow("Server error");
  });
});
