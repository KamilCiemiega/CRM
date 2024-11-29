import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export type Company = {
    id: number;
    name: string;
    email: string;
    phoneNumber: string;
    address: string;
    createdAt: number;
};

export type Client = {
    id: number;
    name: string;
    surname: string;
    email: string;
    phone: string;
    address: string;
    usersDTO: null | Record<string, unknown>;
    company: null | Company;
};

type FilteredEntity = {
    name: string;
    image: string;
};

type ActionType = {
    viewType: 'clients' | 'companies';
    clientsData: Client[];
    searchValue: string;
};

const initialState: ActionType = {
    viewType: "clients",
    clientsData: [],
    searchValue: ''
};

const clientViewSlice = createSlice({
    name: "clientView",
    initialState,
    reducers: {
        setViewType(state, action: PayloadAction<"clients" | "companies">) {
            state.viewType = action.payload;
        },
        setClientsData(state, action: PayloadAction<Client[]>) {
            state.clientsData = action.payload;
        },
        setSearchValue(state, action: PayloadAction<string>){
          state.searchValue = action.payload
        }
    },
});

export const clientViewAction = clientViewSlice.actions;

export default clientViewSlice;