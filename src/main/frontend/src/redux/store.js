import { configureStore } from "@reduxjs/toolkit";
import userReducer from "../redux/userSlice";
import pageReducer from "../redux/currentPageSlice";

export const store = configureStore({
    reducer: {
        user: userReducer,
        page: pageReducer,
    },
})