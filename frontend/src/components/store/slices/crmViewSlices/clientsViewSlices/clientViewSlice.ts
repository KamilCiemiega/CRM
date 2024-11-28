import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type Client = {
    id: number;
    name: string;
    surname: string;
    email: string;
    phone: string;
    address: string;
    usersDTO: null | any;
    company: null | {
        id: number;
        name: string;
        email: string;
        phoneNumber: string;
        address: string;
        createdAt: number;
    };
};

type ActionType = {
    viewType: string;
    clientsData: Client[];
};

const initialState: ActionType = {
    viewType: 'clients',
    clientsData: []
};

const clientViewSlice = createSlice({
    name: "clientView",
    initialState,
    reducers: {
        setViewType(state, action: PayloadAction<string>) {
            state.viewType = action.payload;
        },
        setClientsData(state, action: PayloadAction<Client[]>) {
            state.clientsData = action.payload;
        }
    },
});

export const clientViewAction = clientViewSlice.actions;

export default clientViewSlice;