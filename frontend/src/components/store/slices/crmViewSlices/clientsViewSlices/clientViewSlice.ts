import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ExpandedClient } from "../../../../clientsSection/topPanel/listOfClientsCompany/helperfunctions/initializeData";
import { ExpandedCompany } from "../../../../clientsSection/topPanel/listOfClientsCompany/helperfunctions/initializeData";

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

type ActionType = {
    viewType: 'clients' | 'companies';
    clientsData: Client[];
    searchValue: string;
    clickedEntity: ExpandedClient | ExpandedCompany | null;
    openNewEntityDialog: boolean;
    expandedCompanyData: ExpandedCompany[];
    apiRequestStatus: {status: string, message: string}
};

const initialState: ActionType = {
    viewType: "clients",
    clientsData: [],
    searchValue: '',
    clickedEntity: null,
    openNewEntityDialog: true,
    expandedCompanyData: [],
    apiRequestStatus: {status: "", message: ""}
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
        },
        setClickedEntity(state, action: PayloadAction<ExpandedClient | ExpandedCompany>){
          state.clickedEntity = action.payload;
        },
        setOpenNewEntityDialog(state, action: PayloadAction<boolean>){
          state.openNewEntityDialog = action.payload;
        },
        setExpandedCompanyData(state, action: PayloadAction<ExpandedCompany[]>){
          state.expandedCompanyData = action.payload;
        },
        setApiRequestStatus(state, action: PayloadAction<{status: string, message: string}>){
          state.apiRequestStatus = action.payload;
        }
    },
});

export const clientViewAction = clientViewSlice.actions;

export default clientViewSlice;