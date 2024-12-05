import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ExpandedClient } from "../../../../clientsSection/topPanel/listOfClientsCompany/helperfunctions/initializeData";
import { ExpandedCompany } from "../../../../clientsSection/topPanel/listOfClientsCompany/helperfunctions/initializeData";
import { act } from "react";

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
    companyData: Company[];
    searchValue: string;
    clickedEntity: ExpandedClient | ExpandedCompany | null;
    openNewEntityDialog: boolean;
    expandedCompanyData: ExpandedCompany[];
    apiRequestStatus: {status: string, message: string};
    selectedNewClient: { messageId: number,email: string};
    openEditEntityDialog: boolean;
};

const initialState: ActionType = {
    viewType: "clients",
    clientsData: [],
    companyData: [],
    searchValue: '',
    clickedEntity: null,
    openNewEntityDialog: false,
    expandedCompanyData: [],
    apiRequestStatus: {status: "", message: ""},
    selectedNewClient: {messageId: 0, email: "" },
    openEditEntityDialog: true
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
        setCompanyData(state, action: PayloadAction<Company[]>){
          state.companyData = action.payload;
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
        },
        setSelectedNewClient(state, action: PayloadAction<{messageId: number, email: string}>){
          state.selectedNewClient = action.payload;
        },
        setOpenEditEntityDialog(state, action: PayloadAction<boolean>){
          state.openEditEntityDialog = action.payload;
        }
    },
});

export const clientViewAction = clientViewSlice.actions;

export default clientViewSlice;