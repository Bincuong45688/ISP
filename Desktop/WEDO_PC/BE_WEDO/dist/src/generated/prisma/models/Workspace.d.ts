import type * as runtime from "@prisma/client/runtime/client";
import type * as Prisma from "../internal/prismaNamespace.js";
export type WorkspaceModel = runtime.Types.Result.DefaultSelection<Prisma.$WorkspacePayload>;
export type AggregateWorkspace = {
    _count: WorkspaceCountAggregateOutputType | null;
    _min: WorkspaceMinAggregateOutputType | null;
    _max: WorkspaceMaxAggregateOutputType | null;
};
export type WorkspaceMinAggregateOutputType = {
    id: string | null;
    name: string | null;
    description: string | null;
    ownerId: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type WorkspaceMaxAggregateOutputType = {
    id: string | null;
    name: string | null;
    description: string | null;
    ownerId: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type WorkspaceCountAggregateOutputType = {
    id: number;
    name: number;
    description: number;
    ownerId: number;
    createdAt: number;
    updatedAt: number;
    _all: number;
};
export type WorkspaceMinAggregateInputType = {
    id?: true;
    name?: true;
    description?: true;
    ownerId?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type WorkspaceMaxAggregateInputType = {
    id?: true;
    name?: true;
    description?: true;
    ownerId?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type WorkspaceCountAggregateInputType = {
    id?: true;
    name?: true;
    description?: true;
    ownerId?: true;
    createdAt?: true;
    updatedAt?: true;
    _all?: true;
};
export type WorkspaceAggregateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.WorkspaceWhereInput;
    orderBy?: Prisma.WorkspaceOrderByWithRelationInput | Prisma.WorkspaceOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceWhereUniqueInput;
    take?: number;
    skip?: number;
    _count?: true | WorkspaceCountAggregateInputType;
    _min?: WorkspaceMinAggregateInputType;
    _max?: WorkspaceMaxAggregateInputType;
};
export type GetWorkspaceAggregateType<T extends WorkspaceAggregateArgs> = {
    [P in keyof T & keyof AggregateWorkspace]: P extends '_count' | 'count' ? T[P] extends true ? number : Prisma.GetScalarType<T[P], AggregateWorkspace[P]> : Prisma.GetScalarType<T[P], AggregateWorkspace[P]>;
};
export type WorkspaceGroupByArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.WorkspaceWhereInput;
    orderBy?: Prisma.WorkspaceOrderByWithAggregationInput | Prisma.WorkspaceOrderByWithAggregationInput[];
    by: Prisma.WorkspaceScalarFieldEnum[] | Prisma.WorkspaceScalarFieldEnum;
    having?: Prisma.WorkspaceScalarWhereWithAggregatesInput;
    take?: number;
    skip?: number;
    _count?: WorkspaceCountAggregateInputType | true;
    _min?: WorkspaceMinAggregateInputType;
    _max?: WorkspaceMaxAggregateInputType;
};
export type WorkspaceGroupByOutputType = {
    id: string;
    name: string;
    description: string | null;
    ownerId: string;
    createdAt: Date;
    updatedAt: Date;
    _count: WorkspaceCountAggregateOutputType | null;
    _min: WorkspaceMinAggregateOutputType | null;
    _max: WorkspaceMaxAggregateOutputType | null;
};
export type GetWorkspaceGroupByPayload<T extends WorkspaceGroupByArgs> = Prisma.PrismaPromise<Array<Prisma.PickEnumerable<WorkspaceGroupByOutputType, T['by']> & {
    [P in ((keyof T) & (keyof WorkspaceGroupByOutputType))]: P extends '_count' ? T[P] extends boolean ? number : Prisma.GetScalarType<T[P], WorkspaceGroupByOutputType[P]> : Prisma.GetScalarType<T[P], WorkspaceGroupByOutputType[P]>;
}>>;
export type WorkspaceWhereInput = {
    AND?: Prisma.WorkspaceWhereInput | Prisma.WorkspaceWhereInput[];
    OR?: Prisma.WorkspaceWhereInput[];
    NOT?: Prisma.WorkspaceWhereInput | Prisma.WorkspaceWhereInput[];
    id?: Prisma.StringFilter<"Workspace"> | string;
    name?: Prisma.StringFilter<"Workspace"> | string;
    description?: Prisma.StringNullableFilter<"Workspace"> | string | null;
    ownerId?: Prisma.StringFilter<"Workspace"> | string;
    createdAt?: Prisma.DateTimeFilter<"Workspace"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Workspace"> | Date | string;
    owner?: Prisma.XOR<Prisma.UserScalarRelationFilter, Prisma.UserWhereInput>;
    members?: Prisma.WorkspaceMemberListRelationFilter;
    projects?: Prisma.ProjectListRelationFilter;
    tasks?: Prisma.TaskListRelationFilter;
    events?: Prisma.EventListRelationFilter;
};
export type WorkspaceOrderByWithRelationInput = {
    id?: Prisma.SortOrder;
    name?: Prisma.SortOrder;
    description?: Prisma.SortOrderInput | Prisma.SortOrder;
    ownerId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    owner?: Prisma.UserOrderByWithRelationInput;
    members?: Prisma.WorkspaceMemberOrderByRelationAggregateInput;
    projects?: Prisma.ProjectOrderByRelationAggregateInput;
    tasks?: Prisma.TaskOrderByRelationAggregateInput;
    events?: Prisma.EventOrderByRelationAggregateInput;
};
export type WorkspaceWhereUniqueInput = Prisma.AtLeast<{
    id?: string;
    AND?: Prisma.WorkspaceWhereInput | Prisma.WorkspaceWhereInput[];
    OR?: Prisma.WorkspaceWhereInput[];
    NOT?: Prisma.WorkspaceWhereInput | Prisma.WorkspaceWhereInput[];
    name?: Prisma.StringFilter<"Workspace"> | string;
    description?: Prisma.StringNullableFilter<"Workspace"> | string | null;
    ownerId?: Prisma.StringFilter<"Workspace"> | string;
    createdAt?: Prisma.DateTimeFilter<"Workspace"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Workspace"> | Date | string;
    owner?: Prisma.XOR<Prisma.UserScalarRelationFilter, Prisma.UserWhereInput>;
    members?: Prisma.WorkspaceMemberListRelationFilter;
    projects?: Prisma.ProjectListRelationFilter;
    tasks?: Prisma.TaskListRelationFilter;
    events?: Prisma.EventListRelationFilter;
}, "id">;
export type WorkspaceOrderByWithAggregationInput = {
    id?: Prisma.SortOrder;
    name?: Prisma.SortOrder;
    description?: Prisma.SortOrderInput | Prisma.SortOrder;
    ownerId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    _count?: Prisma.WorkspaceCountOrderByAggregateInput;
    _max?: Prisma.WorkspaceMaxOrderByAggregateInput;
    _min?: Prisma.WorkspaceMinOrderByAggregateInput;
};
export type WorkspaceScalarWhereWithAggregatesInput = {
    AND?: Prisma.WorkspaceScalarWhereWithAggregatesInput | Prisma.WorkspaceScalarWhereWithAggregatesInput[];
    OR?: Prisma.WorkspaceScalarWhereWithAggregatesInput[];
    NOT?: Prisma.WorkspaceScalarWhereWithAggregatesInput | Prisma.WorkspaceScalarWhereWithAggregatesInput[];
    id?: Prisma.StringWithAggregatesFilter<"Workspace"> | string;
    name?: Prisma.StringWithAggregatesFilter<"Workspace"> | string;
    description?: Prisma.StringNullableWithAggregatesFilter<"Workspace"> | string | null;
    ownerId?: Prisma.StringWithAggregatesFilter<"Workspace"> | string;
    createdAt?: Prisma.DateTimeWithAggregatesFilter<"Workspace"> | Date | string;
    updatedAt?: Prisma.DateTimeWithAggregatesFilter<"Workspace"> | Date | string;
};
export type WorkspaceCreateInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    owner: Prisma.UserCreateNestedOneWithoutOwnedWorkspacesInput;
    members?: Prisma.WorkspaceMemberCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUncheckedCreateInput = {
    id?: string;
    name: string;
    description?: string | null;
    ownerId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    members?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectUncheckedCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    owner?: Prisma.UserUpdateOneRequiredWithoutOwnedWorkspacesNestedInput;
    members?: Prisma.WorkspaceMemberUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    ownerId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    members?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUncheckedUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceCreateManyInput = {
    id?: string;
    name: string;
    description?: string | null;
    ownerId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type WorkspaceUpdateManyMutationInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type WorkspaceUncheckedUpdateManyInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    ownerId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type WorkspaceListRelationFilter = {
    every?: Prisma.WorkspaceWhereInput;
    some?: Prisma.WorkspaceWhereInput;
    none?: Prisma.WorkspaceWhereInput;
};
export type WorkspaceOrderByRelationAggregateInput = {
    _count?: Prisma.SortOrder;
};
export type WorkspaceCountOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    name?: Prisma.SortOrder;
    description?: Prisma.SortOrder;
    ownerId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type WorkspaceMaxOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    name?: Prisma.SortOrder;
    description?: Prisma.SortOrder;
    ownerId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type WorkspaceMinOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    name?: Prisma.SortOrder;
    description?: Prisma.SortOrder;
    ownerId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type WorkspaceScalarRelationFilter = {
    is?: Prisma.WorkspaceWhereInput;
    isNot?: Prisma.WorkspaceWhereInput;
};
export type WorkspaceCreateNestedManyWithoutOwnerInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutOwnerInput, Prisma.WorkspaceUncheckedCreateWithoutOwnerInput> | Prisma.WorkspaceCreateWithoutOwnerInput[] | Prisma.WorkspaceUncheckedCreateWithoutOwnerInput[];
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutOwnerInput | Prisma.WorkspaceCreateOrConnectWithoutOwnerInput[];
    createMany?: Prisma.WorkspaceCreateManyOwnerInputEnvelope;
    connect?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
};
export type WorkspaceUncheckedCreateNestedManyWithoutOwnerInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutOwnerInput, Prisma.WorkspaceUncheckedCreateWithoutOwnerInput> | Prisma.WorkspaceCreateWithoutOwnerInput[] | Prisma.WorkspaceUncheckedCreateWithoutOwnerInput[];
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutOwnerInput | Prisma.WorkspaceCreateOrConnectWithoutOwnerInput[];
    createMany?: Prisma.WorkspaceCreateManyOwnerInputEnvelope;
    connect?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
};
export type WorkspaceUpdateManyWithoutOwnerNestedInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutOwnerInput, Prisma.WorkspaceUncheckedCreateWithoutOwnerInput> | Prisma.WorkspaceCreateWithoutOwnerInput[] | Prisma.WorkspaceUncheckedCreateWithoutOwnerInput[];
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutOwnerInput | Prisma.WorkspaceCreateOrConnectWithoutOwnerInput[];
    upsert?: Prisma.WorkspaceUpsertWithWhereUniqueWithoutOwnerInput | Prisma.WorkspaceUpsertWithWhereUniqueWithoutOwnerInput[];
    createMany?: Prisma.WorkspaceCreateManyOwnerInputEnvelope;
    set?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    disconnect?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    delete?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    connect?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    update?: Prisma.WorkspaceUpdateWithWhereUniqueWithoutOwnerInput | Prisma.WorkspaceUpdateWithWhereUniqueWithoutOwnerInput[];
    updateMany?: Prisma.WorkspaceUpdateManyWithWhereWithoutOwnerInput | Prisma.WorkspaceUpdateManyWithWhereWithoutOwnerInput[];
    deleteMany?: Prisma.WorkspaceScalarWhereInput | Prisma.WorkspaceScalarWhereInput[];
};
export type WorkspaceUncheckedUpdateManyWithoutOwnerNestedInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutOwnerInput, Prisma.WorkspaceUncheckedCreateWithoutOwnerInput> | Prisma.WorkspaceCreateWithoutOwnerInput[] | Prisma.WorkspaceUncheckedCreateWithoutOwnerInput[];
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutOwnerInput | Prisma.WorkspaceCreateOrConnectWithoutOwnerInput[];
    upsert?: Prisma.WorkspaceUpsertWithWhereUniqueWithoutOwnerInput | Prisma.WorkspaceUpsertWithWhereUniqueWithoutOwnerInput[];
    createMany?: Prisma.WorkspaceCreateManyOwnerInputEnvelope;
    set?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    disconnect?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    delete?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    connect?: Prisma.WorkspaceWhereUniqueInput | Prisma.WorkspaceWhereUniqueInput[];
    update?: Prisma.WorkspaceUpdateWithWhereUniqueWithoutOwnerInput | Prisma.WorkspaceUpdateWithWhereUniqueWithoutOwnerInput[];
    updateMany?: Prisma.WorkspaceUpdateManyWithWhereWithoutOwnerInput | Prisma.WorkspaceUpdateManyWithWhereWithoutOwnerInput[];
    deleteMany?: Prisma.WorkspaceScalarWhereInput | Prisma.WorkspaceScalarWhereInput[];
};
export type WorkspaceCreateNestedOneWithoutMembersInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutMembersInput, Prisma.WorkspaceUncheckedCreateWithoutMembersInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutMembersInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceUpdateOneRequiredWithoutMembersNestedInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutMembersInput, Prisma.WorkspaceUncheckedCreateWithoutMembersInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutMembersInput;
    upsert?: Prisma.WorkspaceUpsertWithoutMembersInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.WorkspaceUpdateToOneWithWhereWithoutMembersInput, Prisma.WorkspaceUpdateWithoutMembersInput>, Prisma.WorkspaceUncheckedUpdateWithoutMembersInput>;
};
export type WorkspaceCreateNestedOneWithoutProjectsInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutProjectsInput, Prisma.WorkspaceUncheckedCreateWithoutProjectsInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutProjectsInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceUpdateOneRequiredWithoutProjectsNestedInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutProjectsInput, Prisma.WorkspaceUncheckedCreateWithoutProjectsInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutProjectsInput;
    upsert?: Prisma.WorkspaceUpsertWithoutProjectsInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.WorkspaceUpdateToOneWithWhereWithoutProjectsInput, Prisma.WorkspaceUpdateWithoutProjectsInput>, Prisma.WorkspaceUncheckedUpdateWithoutProjectsInput>;
};
export type WorkspaceCreateNestedOneWithoutTasksInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutTasksInput, Prisma.WorkspaceUncheckedCreateWithoutTasksInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutTasksInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceUpdateOneRequiredWithoutTasksNestedInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutTasksInput, Prisma.WorkspaceUncheckedCreateWithoutTasksInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutTasksInput;
    upsert?: Prisma.WorkspaceUpsertWithoutTasksInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.WorkspaceUpdateToOneWithWhereWithoutTasksInput, Prisma.WorkspaceUpdateWithoutTasksInput>, Prisma.WorkspaceUncheckedUpdateWithoutTasksInput>;
};
export type WorkspaceCreateNestedOneWithoutEventsInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutEventsInput, Prisma.WorkspaceUncheckedCreateWithoutEventsInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutEventsInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceUpdateOneRequiredWithoutEventsNestedInput = {
    create?: Prisma.XOR<Prisma.WorkspaceCreateWithoutEventsInput, Prisma.WorkspaceUncheckedCreateWithoutEventsInput>;
    connectOrCreate?: Prisma.WorkspaceCreateOrConnectWithoutEventsInput;
    upsert?: Prisma.WorkspaceUpsertWithoutEventsInput;
    connect?: Prisma.WorkspaceWhereUniqueInput;
    update?: Prisma.XOR<Prisma.XOR<Prisma.WorkspaceUpdateToOneWithWhereWithoutEventsInput, Prisma.WorkspaceUpdateWithoutEventsInput>, Prisma.WorkspaceUncheckedUpdateWithoutEventsInput>;
};
export type WorkspaceCreateWithoutOwnerInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    members?: Prisma.WorkspaceMemberCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUncheckedCreateWithoutOwnerInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    members?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectUncheckedCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceCreateOrConnectWithoutOwnerInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutOwnerInput, Prisma.WorkspaceUncheckedCreateWithoutOwnerInput>;
};
export type WorkspaceCreateManyOwnerInputEnvelope = {
    data: Prisma.WorkspaceCreateManyOwnerInput | Prisma.WorkspaceCreateManyOwnerInput[];
    skipDuplicates?: boolean;
};
export type WorkspaceUpsertWithWhereUniqueWithoutOwnerInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    update: Prisma.XOR<Prisma.WorkspaceUpdateWithoutOwnerInput, Prisma.WorkspaceUncheckedUpdateWithoutOwnerInput>;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutOwnerInput, Prisma.WorkspaceUncheckedCreateWithoutOwnerInput>;
};
export type WorkspaceUpdateWithWhereUniqueWithoutOwnerInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    data: Prisma.XOR<Prisma.WorkspaceUpdateWithoutOwnerInput, Prisma.WorkspaceUncheckedUpdateWithoutOwnerInput>;
};
export type WorkspaceUpdateManyWithWhereWithoutOwnerInput = {
    where: Prisma.WorkspaceScalarWhereInput;
    data: Prisma.XOR<Prisma.WorkspaceUpdateManyMutationInput, Prisma.WorkspaceUncheckedUpdateManyWithoutOwnerInput>;
};
export type WorkspaceScalarWhereInput = {
    AND?: Prisma.WorkspaceScalarWhereInput | Prisma.WorkspaceScalarWhereInput[];
    OR?: Prisma.WorkspaceScalarWhereInput[];
    NOT?: Prisma.WorkspaceScalarWhereInput | Prisma.WorkspaceScalarWhereInput[];
    id?: Prisma.StringFilter<"Workspace"> | string;
    name?: Prisma.StringFilter<"Workspace"> | string;
    description?: Prisma.StringNullableFilter<"Workspace"> | string | null;
    ownerId?: Prisma.StringFilter<"Workspace"> | string;
    createdAt?: Prisma.DateTimeFilter<"Workspace"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Workspace"> | Date | string;
};
export type WorkspaceCreateWithoutMembersInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    owner: Prisma.UserCreateNestedOneWithoutOwnedWorkspacesInput;
    projects?: Prisma.ProjectCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUncheckedCreateWithoutMembersInput = {
    id?: string;
    name: string;
    description?: string | null;
    ownerId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    projects?: Prisma.ProjectUncheckedCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceCreateOrConnectWithoutMembersInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutMembersInput, Prisma.WorkspaceUncheckedCreateWithoutMembersInput>;
};
export type WorkspaceUpsertWithoutMembersInput = {
    update: Prisma.XOR<Prisma.WorkspaceUpdateWithoutMembersInput, Prisma.WorkspaceUncheckedUpdateWithoutMembersInput>;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutMembersInput, Prisma.WorkspaceUncheckedCreateWithoutMembersInput>;
    where?: Prisma.WorkspaceWhereInput;
};
export type WorkspaceUpdateToOneWithWhereWithoutMembersInput = {
    where?: Prisma.WorkspaceWhereInput;
    data: Prisma.XOR<Prisma.WorkspaceUpdateWithoutMembersInput, Prisma.WorkspaceUncheckedUpdateWithoutMembersInput>;
};
export type WorkspaceUpdateWithoutMembersInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    owner?: Prisma.UserUpdateOneRequiredWithoutOwnedWorkspacesNestedInput;
    projects?: Prisma.ProjectUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateWithoutMembersInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    ownerId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    projects?: Prisma.ProjectUncheckedUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceCreateWithoutProjectsInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    owner: Prisma.UserCreateNestedOneWithoutOwnedWorkspacesInput;
    members?: Prisma.WorkspaceMemberCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUncheckedCreateWithoutProjectsInput = {
    id?: string;
    name: string;
    description?: string | null;
    ownerId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    members?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceCreateOrConnectWithoutProjectsInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutProjectsInput, Prisma.WorkspaceUncheckedCreateWithoutProjectsInput>;
};
export type WorkspaceUpsertWithoutProjectsInput = {
    update: Prisma.XOR<Prisma.WorkspaceUpdateWithoutProjectsInput, Prisma.WorkspaceUncheckedUpdateWithoutProjectsInput>;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutProjectsInput, Prisma.WorkspaceUncheckedCreateWithoutProjectsInput>;
    where?: Prisma.WorkspaceWhereInput;
};
export type WorkspaceUpdateToOneWithWhereWithoutProjectsInput = {
    where?: Prisma.WorkspaceWhereInput;
    data: Prisma.XOR<Prisma.WorkspaceUpdateWithoutProjectsInput, Prisma.WorkspaceUncheckedUpdateWithoutProjectsInput>;
};
export type WorkspaceUpdateWithoutProjectsInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    owner?: Prisma.UserUpdateOneRequiredWithoutOwnedWorkspacesNestedInput;
    members?: Prisma.WorkspaceMemberUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateWithoutProjectsInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    ownerId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    members?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceCreateWithoutTasksInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    owner: Prisma.UserCreateNestedOneWithoutOwnedWorkspacesInput;
    members?: Prisma.WorkspaceMemberCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUncheckedCreateWithoutTasksInput = {
    id?: string;
    name: string;
    description?: string | null;
    ownerId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    members?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectUncheckedCreateNestedManyWithoutWorkspaceInput;
    events?: Prisma.EventUncheckedCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceCreateOrConnectWithoutTasksInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutTasksInput, Prisma.WorkspaceUncheckedCreateWithoutTasksInput>;
};
export type WorkspaceUpsertWithoutTasksInput = {
    update: Prisma.XOR<Prisma.WorkspaceUpdateWithoutTasksInput, Prisma.WorkspaceUncheckedUpdateWithoutTasksInput>;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutTasksInput, Prisma.WorkspaceUncheckedCreateWithoutTasksInput>;
    where?: Prisma.WorkspaceWhereInput;
};
export type WorkspaceUpdateToOneWithWhereWithoutTasksInput = {
    where?: Prisma.WorkspaceWhereInput;
    data: Prisma.XOR<Prisma.WorkspaceUpdateWithoutTasksInput, Prisma.WorkspaceUncheckedUpdateWithoutTasksInput>;
};
export type WorkspaceUpdateWithoutTasksInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    owner?: Prisma.UserUpdateOneRequiredWithoutOwnedWorkspacesNestedInput;
    members?: Prisma.WorkspaceMemberUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateWithoutTasksInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    ownerId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    members?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUncheckedUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceCreateWithoutEventsInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    owner: Prisma.UserCreateNestedOneWithoutOwnedWorkspacesInput;
    members?: Prisma.WorkspaceMemberCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceUncheckedCreateWithoutEventsInput = {
    id?: string;
    name: string;
    description?: string | null;
    ownerId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    members?: Prisma.WorkspaceMemberUncheckedCreateNestedManyWithoutWorkspaceInput;
    projects?: Prisma.ProjectUncheckedCreateNestedManyWithoutWorkspaceInput;
    tasks?: Prisma.TaskUncheckedCreateNestedManyWithoutWorkspaceInput;
};
export type WorkspaceCreateOrConnectWithoutEventsInput = {
    where: Prisma.WorkspaceWhereUniqueInput;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutEventsInput, Prisma.WorkspaceUncheckedCreateWithoutEventsInput>;
};
export type WorkspaceUpsertWithoutEventsInput = {
    update: Prisma.XOR<Prisma.WorkspaceUpdateWithoutEventsInput, Prisma.WorkspaceUncheckedUpdateWithoutEventsInput>;
    create: Prisma.XOR<Prisma.WorkspaceCreateWithoutEventsInput, Prisma.WorkspaceUncheckedCreateWithoutEventsInput>;
    where?: Prisma.WorkspaceWhereInput;
};
export type WorkspaceUpdateToOneWithWhereWithoutEventsInput = {
    where?: Prisma.WorkspaceWhereInput;
    data: Prisma.XOR<Prisma.WorkspaceUpdateWithoutEventsInput, Prisma.WorkspaceUncheckedUpdateWithoutEventsInput>;
};
export type WorkspaceUpdateWithoutEventsInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    owner?: Prisma.UserUpdateOneRequiredWithoutOwnedWorkspacesNestedInput;
    members?: Prisma.WorkspaceMemberUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateWithoutEventsInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    ownerId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    members?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUncheckedUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceCreateManyOwnerInput = {
    id?: string;
    name: string;
    description?: string | null;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type WorkspaceUpdateWithoutOwnerInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    members?: Prisma.WorkspaceMemberUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateWithoutOwnerInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    members?: Prisma.WorkspaceMemberUncheckedUpdateManyWithoutWorkspaceNestedInput;
    projects?: Prisma.ProjectUncheckedUpdateManyWithoutWorkspaceNestedInput;
    tasks?: Prisma.TaskUncheckedUpdateManyWithoutWorkspaceNestedInput;
    events?: Prisma.EventUncheckedUpdateManyWithoutWorkspaceNestedInput;
};
export type WorkspaceUncheckedUpdateManyWithoutOwnerInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    name?: Prisma.StringFieldUpdateOperationsInput | string;
    description?: Prisma.NullableStringFieldUpdateOperationsInput | string | null;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type WorkspaceCountOutputType = {
    members: number;
    projects: number;
    tasks: number;
    events: number;
};
export type WorkspaceCountOutputTypeSelect<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    members?: boolean | WorkspaceCountOutputTypeCountMembersArgs;
    projects?: boolean | WorkspaceCountOutputTypeCountProjectsArgs;
    tasks?: boolean | WorkspaceCountOutputTypeCountTasksArgs;
    events?: boolean | WorkspaceCountOutputTypeCountEventsArgs;
};
export type WorkspaceCountOutputTypeDefaultArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceCountOutputTypeSelect<ExtArgs> | null;
};
export type WorkspaceCountOutputTypeCountMembersArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.WorkspaceMemberWhereInput;
};
export type WorkspaceCountOutputTypeCountProjectsArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.ProjectWhereInput;
};
export type WorkspaceCountOutputTypeCountTasksArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.TaskWhereInput;
};
export type WorkspaceCountOutputTypeCountEventsArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.EventWhereInput;
};
export type WorkspaceSelect<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    name?: boolean;
    description?: boolean;
    ownerId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    owner?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
    members?: boolean | Prisma.Workspace$membersArgs<ExtArgs>;
    projects?: boolean | Prisma.Workspace$projectsArgs<ExtArgs>;
    tasks?: boolean | Prisma.Workspace$tasksArgs<ExtArgs>;
    events?: boolean | Prisma.Workspace$eventsArgs<ExtArgs>;
    _count?: boolean | Prisma.WorkspaceCountOutputTypeDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["workspace"]>;
export type WorkspaceSelectCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    name?: boolean;
    description?: boolean;
    ownerId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    owner?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["workspace"]>;
export type WorkspaceSelectUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    name?: boolean;
    description?: boolean;
    ownerId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    owner?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["workspace"]>;
export type WorkspaceSelectScalar = {
    id?: boolean;
    name?: boolean;
    description?: boolean;
    ownerId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
};
export type WorkspaceOmit<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetOmit<"id" | "name" | "description" | "ownerId" | "createdAt" | "updatedAt", ExtArgs["result"]["workspace"]>;
export type WorkspaceInclude<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    owner?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
    members?: boolean | Prisma.Workspace$membersArgs<ExtArgs>;
    projects?: boolean | Prisma.Workspace$projectsArgs<ExtArgs>;
    tasks?: boolean | Prisma.Workspace$tasksArgs<ExtArgs>;
    events?: boolean | Prisma.Workspace$eventsArgs<ExtArgs>;
    _count?: boolean | Prisma.WorkspaceCountOutputTypeDefaultArgs<ExtArgs>;
};
export type WorkspaceIncludeCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    owner?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
};
export type WorkspaceIncludeUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    owner?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
};
export type $WorkspacePayload<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    name: "Workspace";
    objects: {
        owner: Prisma.$UserPayload<ExtArgs>;
        members: Prisma.$WorkspaceMemberPayload<ExtArgs>[];
        projects: Prisma.$ProjectPayload<ExtArgs>[];
        tasks: Prisma.$TaskPayload<ExtArgs>[];
        events: Prisma.$EventPayload<ExtArgs>[];
    };
    scalars: runtime.Types.Extensions.GetPayloadResult<{
        id: string;
        name: string;
        description: string | null;
        ownerId: string;
        createdAt: Date;
        updatedAt: Date;
    }, ExtArgs["result"]["workspace"]>;
    composites: {};
};
export type WorkspaceGetPayload<S extends boolean | null | undefined | WorkspaceDefaultArgs> = runtime.Types.Result.GetResult<Prisma.$WorkspacePayload, S>;
export type WorkspaceCountArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = Omit<WorkspaceFindManyArgs, 'select' | 'include' | 'distinct' | 'omit'> & {
    select?: WorkspaceCountAggregateInputType | true;
};
export interface WorkspaceDelegate<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> {
    [K: symbol]: {
        types: Prisma.TypeMap<ExtArgs>['model']['Workspace'];
        meta: {
            name: 'Workspace';
        };
    };
    findUnique<T extends WorkspaceFindUniqueArgs>(args: Prisma.SelectSubset<T, WorkspaceFindUniqueArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findUnique", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findUniqueOrThrow<T extends WorkspaceFindUniqueOrThrowArgs>(args: Prisma.SelectSubset<T, WorkspaceFindUniqueOrThrowArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findFirst<T extends WorkspaceFindFirstArgs>(args?: Prisma.SelectSubset<T, WorkspaceFindFirstArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findFirst", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findFirstOrThrow<T extends WorkspaceFindFirstOrThrowArgs>(args?: Prisma.SelectSubset<T, WorkspaceFindFirstOrThrowArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findFirstOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findMany<T extends WorkspaceFindManyArgs>(args?: Prisma.SelectSubset<T, WorkspaceFindManyArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findMany", GlobalOmitOptions>>;
    create<T extends WorkspaceCreateArgs>(args: Prisma.SelectSubset<T, WorkspaceCreateArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "create", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    createMany<T extends WorkspaceCreateManyArgs>(args?: Prisma.SelectSubset<T, WorkspaceCreateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    createManyAndReturn<T extends WorkspaceCreateManyAndReturnArgs>(args?: Prisma.SelectSubset<T, WorkspaceCreateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "createManyAndReturn", GlobalOmitOptions>>;
    delete<T extends WorkspaceDeleteArgs>(args: Prisma.SelectSubset<T, WorkspaceDeleteArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "delete", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    update<T extends WorkspaceUpdateArgs>(args: Prisma.SelectSubset<T, WorkspaceUpdateArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "update", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    deleteMany<T extends WorkspaceDeleteManyArgs>(args?: Prisma.SelectSubset<T, WorkspaceDeleteManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateMany<T extends WorkspaceUpdateManyArgs>(args: Prisma.SelectSubset<T, WorkspaceUpdateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateManyAndReturn<T extends WorkspaceUpdateManyAndReturnArgs>(args: Prisma.SelectSubset<T, WorkspaceUpdateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "updateManyAndReturn", GlobalOmitOptions>>;
    upsert<T extends WorkspaceUpsertArgs>(args: Prisma.SelectSubset<T, WorkspaceUpsertArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "upsert", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    count<T extends WorkspaceCountArgs>(args?: Prisma.Subset<T, WorkspaceCountArgs>): Prisma.PrismaPromise<T extends runtime.Types.Utils.Record<'select', any> ? T['select'] extends true ? number : Prisma.GetScalarType<T['select'], WorkspaceCountAggregateOutputType> : number>;
    aggregate<T extends WorkspaceAggregateArgs>(args: Prisma.Subset<T, WorkspaceAggregateArgs>): Prisma.PrismaPromise<GetWorkspaceAggregateType<T>>;
    groupBy<T extends WorkspaceGroupByArgs, HasSelectOrTake extends Prisma.Or<Prisma.Extends<'skip', Prisma.Keys<T>>, Prisma.Extends<'take', Prisma.Keys<T>>>, OrderByArg extends Prisma.True extends HasSelectOrTake ? {
        orderBy: WorkspaceGroupByArgs['orderBy'];
    } : {
        orderBy?: WorkspaceGroupByArgs['orderBy'];
    }, OrderFields extends Prisma.ExcludeUnderscoreKeys<Prisma.Keys<Prisma.MaybeTupleToUnion<T['orderBy']>>>, ByFields extends Prisma.MaybeTupleToUnion<T['by']>, ByValid extends Prisma.Has<ByFields, OrderFields>, HavingFields extends Prisma.GetHavingFields<T['having']>, HavingValid extends Prisma.Has<ByFields, HavingFields>, ByEmpty extends T['by'] extends never[] ? Prisma.True : Prisma.False, InputErrors extends ByEmpty extends Prisma.True ? `Error: "by" must not be empty.` : HavingValid extends Prisma.False ? {
        [P in HavingFields]: P extends ByFields ? never : P extends string ? `Error: Field "${P}" used in "having" needs to be provided in "by".` : [
            Error,
            'Field ',
            P,
            ` in "having" needs to be provided in "by"`
        ];
    }[HavingFields] : 'take' extends Prisma.Keys<T> ? 'orderBy' extends Prisma.Keys<T> ? ByValid extends Prisma.True ? {} : {
        [P in OrderFields]: P extends ByFields ? never : `Error: Field "${P}" in "orderBy" needs to be provided in "by"`;
    }[OrderFields] : 'Error: If you provide "take", you also need to provide "orderBy"' : 'skip' extends Prisma.Keys<T> ? 'orderBy' extends Prisma.Keys<T> ? ByValid extends Prisma.True ? {} : {
        [P in OrderFields]: P extends ByFields ? never : `Error: Field "${P}" in "orderBy" needs to be provided in "by"`;
    }[OrderFields] : 'Error: If you provide "skip", you also need to provide "orderBy"' : ByValid extends Prisma.True ? {} : {
        [P in OrderFields]: P extends ByFields ? never : `Error: Field "${P}" in "orderBy" needs to be provided in "by"`;
    }[OrderFields]>(args: Prisma.SubsetIntersection<T, WorkspaceGroupByArgs, OrderByArg> & InputErrors): {} extends InputErrors ? GetWorkspaceGroupByPayload<T> : Prisma.PrismaPromise<InputErrors>;
    readonly fields: WorkspaceFieldRefs;
}
export interface Prisma__WorkspaceClient<T, Null = never, ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> extends Prisma.PrismaPromise<T> {
    readonly [Symbol.toStringTag]: "PrismaPromise";
    owner<T extends Prisma.UserDefaultArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.UserDefaultArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions> | Null, Null, ExtArgs, GlobalOmitOptions>;
    members<T extends Prisma.Workspace$membersArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.Workspace$membersArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$WorkspaceMemberPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    projects<T extends Prisma.Workspace$projectsArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.Workspace$projectsArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$ProjectPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    tasks<T extends Prisma.Workspace$tasksArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.Workspace$tasksArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$TaskPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    events<T extends Prisma.Workspace$eventsArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.Workspace$eventsArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findMany", GlobalOmitOptions> | Null>;
    then<TResult1 = T, TResult2 = never>(onfulfilled?: ((value: T) => TResult1 | PromiseLike<TResult1>) | undefined | null, onrejected?: ((reason: any) => TResult2 | PromiseLike<TResult2>) | undefined | null): runtime.Types.Utils.JsPromise<TResult1 | TResult2>;
    catch<TResult = never>(onrejected?: ((reason: any) => TResult | PromiseLike<TResult>) | undefined | null): runtime.Types.Utils.JsPromise<T | TResult>;
    finally(onfinally?: (() => void) | undefined | null): runtime.Types.Utils.JsPromise<T>;
}
export interface WorkspaceFieldRefs {
    readonly id: Prisma.FieldRef<"Workspace", 'String'>;
    readonly name: Prisma.FieldRef<"Workspace", 'String'>;
    readonly description: Prisma.FieldRef<"Workspace", 'String'>;
    readonly ownerId: Prisma.FieldRef<"Workspace", 'String'>;
    readonly createdAt: Prisma.FieldRef<"Workspace", 'DateTime'>;
    readonly updatedAt: Prisma.FieldRef<"Workspace", 'DateTime'>;
}
export type WorkspaceFindUniqueArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceFindUniqueOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceFindFirstArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where?: Prisma.WorkspaceWhereInput;
    orderBy?: Prisma.WorkspaceOrderByWithRelationInput | Prisma.WorkspaceOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.WorkspaceScalarFieldEnum | Prisma.WorkspaceScalarFieldEnum[];
};
export type WorkspaceFindFirstOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where?: Prisma.WorkspaceWhereInput;
    orderBy?: Prisma.WorkspaceOrderByWithRelationInput | Prisma.WorkspaceOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.WorkspaceScalarFieldEnum | Prisma.WorkspaceScalarFieldEnum[];
};
export type WorkspaceFindManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where?: Prisma.WorkspaceWhereInput;
    orderBy?: Prisma.WorkspaceOrderByWithRelationInput | Prisma.WorkspaceOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.WorkspaceScalarFieldEnum | Prisma.WorkspaceScalarFieldEnum[];
};
export type WorkspaceCreateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.WorkspaceCreateInput, Prisma.WorkspaceUncheckedCreateInput>;
};
export type WorkspaceCreateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.WorkspaceCreateManyInput | Prisma.WorkspaceCreateManyInput[];
    skipDuplicates?: boolean;
};
export type WorkspaceCreateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelectCreateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    data: Prisma.WorkspaceCreateManyInput | Prisma.WorkspaceCreateManyInput[];
    skipDuplicates?: boolean;
    include?: Prisma.WorkspaceIncludeCreateManyAndReturn<ExtArgs> | null;
};
export type WorkspaceUpdateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.WorkspaceUpdateInput, Prisma.WorkspaceUncheckedUpdateInput>;
    where: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceUpdateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.XOR<Prisma.WorkspaceUpdateManyMutationInput, Prisma.WorkspaceUncheckedUpdateManyInput>;
    where?: Prisma.WorkspaceWhereInput;
    limit?: number;
};
export type WorkspaceUpdateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelectUpdateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    data: Prisma.XOR<Prisma.WorkspaceUpdateManyMutationInput, Prisma.WorkspaceUncheckedUpdateManyInput>;
    where?: Prisma.WorkspaceWhereInput;
    limit?: number;
    include?: Prisma.WorkspaceIncludeUpdateManyAndReturn<ExtArgs> | null;
};
export type WorkspaceUpsertArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where: Prisma.WorkspaceWhereUniqueInput;
    create: Prisma.XOR<Prisma.WorkspaceCreateInput, Prisma.WorkspaceUncheckedCreateInput>;
    update: Prisma.XOR<Prisma.WorkspaceUpdateInput, Prisma.WorkspaceUncheckedUpdateInput>;
};
export type WorkspaceDeleteArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
    where: Prisma.WorkspaceWhereUniqueInput;
};
export type WorkspaceDeleteManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.WorkspaceWhereInput;
    limit?: number;
};
export type Workspace$membersArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceMemberSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceMemberOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceMemberInclude<ExtArgs> | null;
    where?: Prisma.WorkspaceMemberWhereInput;
    orderBy?: Prisma.WorkspaceMemberOrderByWithRelationInput | Prisma.WorkspaceMemberOrderByWithRelationInput[];
    cursor?: Prisma.WorkspaceMemberWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.WorkspaceMemberScalarFieldEnum | Prisma.WorkspaceMemberScalarFieldEnum[];
};
export type Workspace$projectsArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.ProjectSelect<ExtArgs> | null;
    omit?: Prisma.ProjectOmit<ExtArgs> | null;
    include?: Prisma.ProjectInclude<ExtArgs> | null;
    where?: Prisma.ProjectWhereInput;
    orderBy?: Prisma.ProjectOrderByWithRelationInput | Prisma.ProjectOrderByWithRelationInput[];
    cursor?: Prisma.ProjectWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.ProjectScalarFieldEnum | Prisma.ProjectScalarFieldEnum[];
};
export type Workspace$tasksArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.TaskSelect<ExtArgs> | null;
    omit?: Prisma.TaskOmit<ExtArgs> | null;
    include?: Prisma.TaskInclude<ExtArgs> | null;
    where?: Prisma.TaskWhereInput;
    orderBy?: Prisma.TaskOrderByWithRelationInput | Prisma.TaskOrderByWithRelationInput[];
    cursor?: Prisma.TaskWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.TaskScalarFieldEnum | Prisma.TaskScalarFieldEnum[];
};
export type Workspace$eventsArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    where?: Prisma.EventWhereInput;
    orderBy?: Prisma.EventOrderByWithRelationInput | Prisma.EventOrderByWithRelationInput[];
    cursor?: Prisma.EventWhereUniqueInput;
    take?: number;
    skip?: number;
    distinct?: Prisma.EventScalarFieldEnum | Prisma.EventScalarFieldEnum[];
};
export type WorkspaceDefaultArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.WorkspaceSelect<ExtArgs> | null;
    omit?: Prisma.WorkspaceOmit<ExtArgs> | null;
    include?: Prisma.WorkspaceInclude<ExtArgs> | null;
};
