import { createSlice } from "@reduxjs/toolkit"

const initialState = {
    isLoggedIn: false,
    username: '',
    isVeryCool: false,
}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setUser: (state, action) => {
            const { username, isVeryCool } = action.payload;
            state.username = username;
            state.isVeryCool = isVeryCool;
            state.isLoggedIn = true;
        },
        clearUser: (state) => {
            state.isLoggedIn = false;
            state.username = '';
            state.isVeryCool = false;
        },
    },
});

export const {setUser, clearUser} = userSlice.actions;

export default userSlice.reducer;