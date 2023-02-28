import { createSlice } from "@reduxjs/toolkit"

export interface PageData {
    page: string;
}

const initialState: PageData = {
    page: 'admin'
}

export const currentPageSlice = createSlice({
    name: 'page',
    initialState,
    reducers: {
        setPage: (state, action) => {
            const { page } = action.payload;
            state.page = page;
        },
    },
});

export const {setPage} = currentPageSlice.actions;

export default currentPageSlice.reducer;