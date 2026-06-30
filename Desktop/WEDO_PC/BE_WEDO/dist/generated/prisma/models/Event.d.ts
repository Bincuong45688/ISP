import type * as runtime from "@prisma/client/runtime/client";
import type * as Prisma from "../internal/prismaNamespace.js";
export type EventModel = runtime.Types.Result.DefaultSelection<Prisma.$EventPayload>;
export type AggregateEvent = {
    _count: EventCountAggregateOutputType | null;
    _min: EventMinAggregateOutputType | null;
    _max: EventMaxAggregateOutputType | null;
};
export type EventMinAggregateOutputType = {
    id: string | null;
    title: string | null;
    startTime: Date | null;
    endTime: Date | null;
    workspaceId: string | null;
    creatorId: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type EventMaxAggregateOutputType = {
    id: string | null;
    title: string | null;
    startTime: Date | null;
    endTime: Date | null;
    workspaceId: string | null;
    creatorId: string | null;
    createdAt: Date | null;
    updatedAt: Date | null;
};
export type EventCountAggregateOutputType = {
    id: number;
    title: number;
    startTime: number;
    endTime: number;
    workspaceId: number;
    creatorId: number;
    createdAt: number;
    updatedAt: number;
    _all: number;
};
export type EventMinAggregateInputType = {
    id?: true;
    title?: true;
    startTime?: true;
    endTime?: true;
    workspaceId?: true;
    creatorId?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type EventMaxAggregateInputType = {
    id?: true;
    title?: true;
    startTime?: true;
    endTime?: true;
    workspaceId?: true;
    creatorId?: true;
    createdAt?: true;
    updatedAt?: true;
};
export type EventCountAggregateInputType = {
    id?: true;
    title?: true;
    startTime?: true;
    endTime?: true;
    workspaceId?: true;
    creatorId?: true;
    createdAt?: true;
    updatedAt?: true;
    _all?: true;
};
export type EventAggregateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.EventWhereInput;
    orderBy?: Prisma.EventOrderByWithRelationInput | Prisma.EventOrderByWithRelationInput[];
    cursor?: Prisma.EventWhereUniqueInput;
    take?: number;
    skip?: number;
    _count?: true | EventCountAggregateInputType;
    _min?: EventMinAggregateInputType;
    _max?: EventMaxAggregateInputType;
};
export type GetEventAggregateType<T extends EventAggregateArgs> = {
    [P in keyof T & keyof AggregateEvent]: P extends '_count' | 'count' ? T[P] extends true ? number : Prisma.GetScalarType<T[P], AggregateEvent[P]> : Prisma.GetScalarType<T[P], AggregateEvent[P]>;
};
export type EventGroupByArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.EventWhereInput;
    orderBy?: Prisma.EventOrderByWithAggregationInput | Prisma.EventOrderByWithAggregationInput[];
    by: Prisma.EventScalarFieldEnum[] | Prisma.EventScalarFieldEnum;
    having?: Prisma.EventScalarWhereWithAggregatesInput;
    take?: number;
    skip?: number;
    _count?: EventCountAggregateInputType | true;
    _min?: EventMinAggregateInputType;
    _max?: EventMaxAggregateInputType;
};
export type EventGroupByOutputType = {
    id: string;
    title: string;
    startTime: Date;
    endTime: Date;
    workspaceId: string;
    creatorId: string;
    createdAt: Date;
    updatedAt: Date;
    _count: EventCountAggregateOutputType | null;
    _min: EventMinAggregateOutputType | null;
    _max: EventMaxAggregateOutputType | null;
};
export type GetEventGroupByPayload<T extends EventGroupByArgs> = Prisma.PrismaPromise<Array<Prisma.PickEnumerable<EventGroupByOutputType, T['by']> & {
    [P in ((keyof T) & (keyof EventGroupByOutputType))]: P extends '_count' ? T[P] extends boolean ? number : Prisma.GetScalarType<T[P], EventGroupByOutputType[P]> : Prisma.GetScalarType<T[P], EventGroupByOutputType[P]>;
}>>;
export type EventWhereInput = {
    AND?: Prisma.EventWhereInput | Prisma.EventWhereInput[];
    OR?: Prisma.EventWhereInput[];
    NOT?: Prisma.EventWhereInput | Prisma.EventWhereInput[];
    id?: Prisma.StringFilter<"Event"> | string;
    title?: Prisma.StringFilter<"Event"> | string;
    startTime?: Prisma.DateTimeFilter<"Event"> | Date | string;
    endTime?: Prisma.DateTimeFilter<"Event"> | Date | string;
    workspaceId?: Prisma.StringFilter<"Event"> | string;
    creatorId?: Prisma.StringFilter<"Event"> | string;
    createdAt?: Prisma.DateTimeFilter<"Event"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Event"> | Date | string;
    workspace?: Prisma.XOR<Prisma.WorkspaceScalarRelationFilter, Prisma.WorkspaceWhereInput>;
    creator?: Prisma.XOR<Prisma.UserScalarRelationFilter, Prisma.UserWhereInput>;
};
export type EventOrderByWithRelationInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    startTime?: Prisma.SortOrder;
    endTime?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    creatorId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    workspace?: Prisma.WorkspaceOrderByWithRelationInput;
    creator?: Prisma.UserOrderByWithRelationInput;
};
export type EventWhereUniqueInput = Prisma.AtLeast<{
    id?: string;
    AND?: Prisma.EventWhereInput | Prisma.EventWhereInput[];
    OR?: Prisma.EventWhereInput[];
    NOT?: Prisma.EventWhereInput | Prisma.EventWhereInput[];
    title?: Prisma.StringFilter<"Event"> | string;
    startTime?: Prisma.DateTimeFilter<"Event"> | Date | string;
    endTime?: Prisma.DateTimeFilter<"Event"> | Date | string;
    workspaceId?: Prisma.StringFilter<"Event"> | string;
    creatorId?: Prisma.StringFilter<"Event"> | string;
    createdAt?: Prisma.DateTimeFilter<"Event"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Event"> | Date | string;
    workspace?: Prisma.XOR<Prisma.WorkspaceScalarRelationFilter, Prisma.WorkspaceWhereInput>;
    creator?: Prisma.XOR<Prisma.UserScalarRelationFilter, Prisma.UserWhereInput>;
}, "id">;
export type EventOrderByWithAggregationInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    startTime?: Prisma.SortOrder;
    endTime?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    creatorId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
    _count?: Prisma.EventCountOrderByAggregateInput;
    _max?: Prisma.EventMaxOrderByAggregateInput;
    _min?: Prisma.EventMinOrderByAggregateInput;
};
export type EventScalarWhereWithAggregatesInput = {
    AND?: Prisma.EventScalarWhereWithAggregatesInput | Prisma.EventScalarWhereWithAggregatesInput[];
    OR?: Prisma.EventScalarWhereWithAggregatesInput[];
    NOT?: Prisma.EventScalarWhereWithAggregatesInput | Prisma.EventScalarWhereWithAggregatesInput[];
    id?: Prisma.StringWithAggregatesFilter<"Event"> | string;
    title?: Prisma.StringWithAggregatesFilter<"Event"> | string;
    startTime?: Prisma.DateTimeWithAggregatesFilter<"Event"> | Date | string;
    endTime?: Prisma.DateTimeWithAggregatesFilter<"Event"> | Date | string;
    workspaceId?: Prisma.StringWithAggregatesFilter<"Event"> | string;
    creatorId?: Prisma.StringWithAggregatesFilter<"Event"> | string;
    createdAt?: Prisma.DateTimeWithAggregatesFilter<"Event"> | Date | string;
    updatedAt?: Prisma.DateTimeWithAggregatesFilter<"Event"> | Date | string;
};
export type EventCreateInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspace: Prisma.WorkspaceCreateNestedOneWithoutEventsInput;
    creator: Prisma.UserCreateNestedOneWithoutEventsInput;
};
export type EventUncheckedCreateInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    workspaceId: string;
    creatorId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type EventUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspace?: Prisma.WorkspaceUpdateOneRequiredWithoutEventsNestedInput;
    creator?: Prisma.UserUpdateOneRequiredWithoutEventsNestedInput;
};
export type EventUncheckedUpdateInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    creatorId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventCreateManyInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    workspaceId: string;
    creatorId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type EventUpdateManyMutationInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventUncheckedUpdateManyInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    creatorId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventListRelationFilter = {
    every?: Prisma.EventWhereInput;
    some?: Prisma.EventWhereInput;
    none?: Prisma.EventWhereInput;
};
export type EventOrderByRelationAggregateInput = {
    _count?: Prisma.SortOrder;
};
export type EventCountOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    startTime?: Prisma.SortOrder;
    endTime?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    creatorId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type EventMaxOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    startTime?: Prisma.SortOrder;
    endTime?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    creatorId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type EventMinOrderByAggregateInput = {
    id?: Prisma.SortOrder;
    title?: Prisma.SortOrder;
    startTime?: Prisma.SortOrder;
    endTime?: Prisma.SortOrder;
    workspaceId?: Prisma.SortOrder;
    creatorId?: Prisma.SortOrder;
    createdAt?: Prisma.SortOrder;
    updatedAt?: Prisma.SortOrder;
};
export type EventCreateNestedManyWithoutCreatorInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutCreatorInput, Prisma.EventUncheckedCreateWithoutCreatorInput> | Prisma.EventCreateWithoutCreatorInput[] | Prisma.EventUncheckedCreateWithoutCreatorInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutCreatorInput | Prisma.EventCreateOrConnectWithoutCreatorInput[];
    createMany?: Prisma.EventCreateManyCreatorInputEnvelope;
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
};
export type EventUncheckedCreateNestedManyWithoutCreatorInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutCreatorInput, Prisma.EventUncheckedCreateWithoutCreatorInput> | Prisma.EventCreateWithoutCreatorInput[] | Prisma.EventUncheckedCreateWithoutCreatorInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutCreatorInput | Prisma.EventCreateOrConnectWithoutCreatorInput[];
    createMany?: Prisma.EventCreateManyCreatorInputEnvelope;
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
};
export type EventUpdateManyWithoutCreatorNestedInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutCreatorInput, Prisma.EventUncheckedCreateWithoutCreatorInput> | Prisma.EventCreateWithoutCreatorInput[] | Prisma.EventUncheckedCreateWithoutCreatorInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutCreatorInput | Prisma.EventCreateOrConnectWithoutCreatorInput[];
    upsert?: Prisma.EventUpsertWithWhereUniqueWithoutCreatorInput | Prisma.EventUpsertWithWhereUniqueWithoutCreatorInput[];
    createMany?: Prisma.EventCreateManyCreatorInputEnvelope;
    set?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    disconnect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    delete?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    update?: Prisma.EventUpdateWithWhereUniqueWithoutCreatorInput | Prisma.EventUpdateWithWhereUniqueWithoutCreatorInput[];
    updateMany?: Prisma.EventUpdateManyWithWhereWithoutCreatorInput | Prisma.EventUpdateManyWithWhereWithoutCreatorInput[];
    deleteMany?: Prisma.EventScalarWhereInput | Prisma.EventScalarWhereInput[];
};
export type EventUncheckedUpdateManyWithoutCreatorNestedInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutCreatorInput, Prisma.EventUncheckedCreateWithoutCreatorInput> | Prisma.EventCreateWithoutCreatorInput[] | Prisma.EventUncheckedCreateWithoutCreatorInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutCreatorInput | Prisma.EventCreateOrConnectWithoutCreatorInput[];
    upsert?: Prisma.EventUpsertWithWhereUniqueWithoutCreatorInput | Prisma.EventUpsertWithWhereUniqueWithoutCreatorInput[];
    createMany?: Prisma.EventCreateManyCreatorInputEnvelope;
    set?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    disconnect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    delete?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    update?: Prisma.EventUpdateWithWhereUniqueWithoutCreatorInput | Prisma.EventUpdateWithWhereUniqueWithoutCreatorInput[];
    updateMany?: Prisma.EventUpdateManyWithWhereWithoutCreatorInput | Prisma.EventUpdateManyWithWhereWithoutCreatorInput[];
    deleteMany?: Prisma.EventScalarWhereInput | Prisma.EventScalarWhereInput[];
};
export type EventCreateNestedManyWithoutWorkspaceInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutWorkspaceInput, Prisma.EventUncheckedCreateWithoutWorkspaceInput> | Prisma.EventCreateWithoutWorkspaceInput[] | Prisma.EventUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutWorkspaceInput | Prisma.EventCreateOrConnectWithoutWorkspaceInput[];
    createMany?: Prisma.EventCreateManyWorkspaceInputEnvelope;
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
};
export type EventUncheckedCreateNestedManyWithoutWorkspaceInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutWorkspaceInput, Prisma.EventUncheckedCreateWithoutWorkspaceInput> | Prisma.EventCreateWithoutWorkspaceInput[] | Prisma.EventUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutWorkspaceInput | Prisma.EventCreateOrConnectWithoutWorkspaceInput[];
    createMany?: Prisma.EventCreateManyWorkspaceInputEnvelope;
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
};
export type EventUpdateManyWithoutWorkspaceNestedInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutWorkspaceInput, Prisma.EventUncheckedCreateWithoutWorkspaceInput> | Prisma.EventCreateWithoutWorkspaceInput[] | Prisma.EventUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutWorkspaceInput | Prisma.EventCreateOrConnectWithoutWorkspaceInput[];
    upsert?: Prisma.EventUpsertWithWhereUniqueWithoutWorkspaceInput | Prisma.EventUpsertWithWhereUniqueWithoutWorkspaceInput[];
    createMany?: Prisma.EventCreateManyWorkspaceInputEnvelope;
    set?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    disconnect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    delete?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    update?: Prisma.EventUpdateWithWhereUniqueWithoutWorkspaceInput | Prisma.EventUpdateWithWhereUniqueWithoutWorkspaceInput[];
    updateMany?: Prisma.EventUpdateManyWithWhereWithoutWorkspaceInput | Prisma.EventUpdateManyWithWhereWithoutWorkspaceInput[];
    deleteMany?: Prisma.EventScalarWhereInput | Prisma.EventScalarWhereInput[];
};
export type EventUncheckedUpdateManyWithoutWorkspaceNestedInput = {
    create?: Prisma.XOR<Prisma.EventCreateWithoutWorkspaceInput, Prisma.EventUncheckedCreateWithoutWorkspaceInput> | Prisma.EventCreateWithoutWorkspaceInput[] | Prisma.EventUncheckedCreateWithoutWorkspaceInput[];
    connectOrCreate?: Prisma.EventCreateOrConnectWithoutWorkspaceInput | Prisma.EventCreateOrConnectWithoutWorkspaceInput[];
    upsert?: Prisma.EventUpsertWithWhereUniqueWithoutWorkspaceInput | Prisma.EventUpsertWithWhereUniqueWithoutWorkspaceInput[];
    createMany?: Prisma.EventCreateManyWorkspaceInputEnvelope;
    set?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    disconnect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    delete?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    connect?: Prisma.EventWhereUniqueInput | Prisma.EventWhereUniqueInput[];
    update?: Prisma.EventUpdateWithWhereUniqueWithoutWorkspaceInput | Prisma.EventUpdateWithWhereUniqueWithoutWorkspaceInput[];
    updateMany?: Prisma.EventUpdateManyWithWhereWithoutWorkspaceInput | Prisma.EventUpdateManyWithWhereWithoutWorkspaceInput[];
    deleteMany?: Prisma.EventScalarWhereInput | Prisma.EventScalarWhereInput[];
};
export type EventCreateWithoutCreatorInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    workspace: Prisma.WorkspaceCreateNestedOneWithoutEventsInput;
};
export type EventUncheckedCreateWithoutCreatorInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    workspaceId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type EventCreateOrConnectWithoutCreatorInput = {
    where: Prisma.EventWhereUniqueInput;
    create: Prisma.XOR<Prisma.EventCreateWithoutCreatorInput, Prisma.EventUncheckedCreateWithoutCreatorInput>;
};
export type EventCreateManyCreatorInputEnvelope = {
    data: Prisma.EventCreateManyCreatorInput | Prisma.EventCreateManyCreatorInput[];
    skipDuplicates?: boolean;
};
export type EventUpsertWithWhereUniqueWithoutCreatorInput = {
    where: Prisma.EventWhereUniqueInput;
    update: Prisma.XOR<Prisma.EventUpdateWithoutCreatorInput, Prisma.EventUncheckedUpdateWithoutCreatorInput>;
    create: Prisma.XOR<Prisma.EventCreateWithoutCreatorInput, Prisma.EventUncheckedCreateWithoutCreatorInput>;
};
export type EventUpdateWithWhereUniqueWithoutCreatorInput = {
    where: Prisma.EventWhereUniqueInput;
    data: Prisma.XOR<Prisma.EventUpdateWithoutCreatorInput, Prisma.EventUncheckedUpdateWithoutCreatorInput>;
};
export type EventUpdateManyWithWhereWithoutCreatorInput = {
    where: Prisma.EventScalarWhereInput;
    data: Prisma.XOR<Prisma.EventUpdateManyMutationInput, Prisma.EventUncheckedUpdateManyWithoutCreatorInput>;
};
export type EventScalarWhereInput = {
    AND?: Prisma.EventScalarWhereInput | Prisma.EventScalarWhereInput[];
    OR?: Prisma.EventScalarWhereInput[];
    NOT?: Prisma.EventScalarWhereInput | Prisma.EventScalarWhereInput[];
    id?: Prisma.StringFilter<"Event"> | string;
    title?: Prisma.StringFilter<"Event"> | string;
    startTime?: Prisma.DateTimeFilter<"Event"> | Date | string;
    endTime?: Prisma.DateTimeFilter<"Event"> | Date | string;
    workspaceId?: Prisma.StringFilter<"Event"> | string;
    creatorId?: Prisma.StringFilter<"Event"> | string;
    createdAt?: Prisma.DateTimeFilter<"Event"> | Date | string;
    updatedAt?: Prisma.DateTimeFilter<"Event"> | Date | string;
};
export type EventCreateWithoutWorkspaceInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
    creator: Prisma.UserCreateNestedOneWithoutEventsInput;
};
export type EventUncheckedCreateWithoutWorkspaceInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    creatorId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type EventCreateOrConnectWithoutWorkspaceInput = {
    where: Prisma.EventWhereUniqueInput;
    create: Prisma.XOR<Prisma.EventCreateWithoutWorkspaceInput, Prisma.EventUncheckedCreateWithoutWorkspaceInput>;
};
export type EventCreateManyWorkspaceInputEnvelope = {
    data: Prisma.EventCreateManyWorkspaceInput | Prisma.EventCreateManyWorkspaceInput[];
    skipDuplicates?: boolean;
};
export type EventUpsertWithWhereUniqueWithoutWorkspaceInput = {
    where: Prisma.EventWhereUniqueInput;
    update: Prisma.XOR<Prisma.EventUpdateWithoutWorkspaceInput, Prisma.EventUncheckedUpdateWithoutWorkspaceInput>;
    create: Prisma.XOR<Prisma.EventCreateWithoutWorkspaceInput, Prisma.EventUncheckedCreateWithoutWorkspaceInput>;
};
export type EventUpdateWithWhereUniqueWithoutWorkspaceInput = {
    where: Prisma.EventWhereUniqueInput;
    data: Prisma.XOR<Prisma.EventUpdateWithoutWorkspaceInput, Prisma.EventUncheckedUpdateWithoutWorkspaceInput>;
};
export type EventUpdateManyWithWhereWithoutWorkspaceInput = {
    where: Prisma.EventScalarWhereInput;
    data: Prisma.XOR<Prisma.EventUpdateManyMutationInput, Prisma.EventUncheckedUpdateManyWithoutWorkspaceInput>;
};
export type EventCreateManyCreatorInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    workspaceId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type EventUpdateWithoutCreatorInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspace?: Prisma.WorkspaceUpdateOneRequiredWithoutEventsNestedInput;
};
export type EventUncheckedUpdateWithoutCreatorInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventUncheckedUpdateManyWithoutCreatorInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    workspaceId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventCreateManyWorkspaceInput = {
    id?: string;
    title: string;
    startTime: Date | string;
    endTime: Date | string;
    creatorId: string;
    createdAt?: Date | string;
    updatedAt?: Date | string;
};
export type EventUpdateWithoutWorkspaceInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    creator?: Prisma.UserUpdateOneRequiredWithoutEventsNestedInput;
};
export type EventUncheckedUpdateWithoutWorkspaceInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    creatorId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventUncheckedUpdateManyWithoutWorkspaceInput = {
    id?: Prisma.StringFieldUpdateOperationsInput | string;
    title?: Prisma.StringFieldUpdateOperationsInput | string;
    startTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    endTime?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    creatorId?: Prisma.StringFieldUpdateOperationsInput | string;
    createdAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
    updatedAt?: Prisma.DateTimeFieldUpdateOperationsInput | Date | string;
};
export type EventSelect<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    title?: boolean;
    startTime?: boolean;
    endTime?: boolean;
    workspaceId?: boolean;
    creatorId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    creator?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["event"]>;
export type EventSelectCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    title?: boolean;
    startTime?: boolean;
    endTime?: boolean;
    workspaceId?: boolean;
    creatorId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    creator?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["event"]>;
export type EventSelectUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetSelect<{
    id?: boolean;
    title?: boolean;
    startTime?: boolean;
    endTime?: boolean;
    workspaceId?: boolean;
    creatorId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    creator?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
}, ExtArgs["result"]["event"]>;
export type EventSelectScalar = {
    id?: boolean;
    title?: boolean;
    startTime?: boolean;
    endTime?: boolean;
    workspaceId?: boolean;
    creatorId?: boolean;
    createdAt?: boolean;
    updatedAt?: boolean;
};
export type EventOmit<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = runtime.Types.Extensions.GetOmit<"id" | "title" | "startTime" | "endTime" | "workspaceId" | "creatorId" | "createdAt" | "updatedAt", ExtArgs["result"]["event"]>;
export type EventInclude<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    creator?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
};
export type EventIncludeCreateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    creator?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
};
export type EventIncludeUpdateManyAndReturn<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    workspace?: boolean | Prisma.WorkspaceDefaultArgs<ExtArgs>;
    creator?: boolean | Prisma.UserDefaultArgs<ExtArgs>;
};
export type $EventPayload<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    name: "Event";
    objects: {
        workspace: Prisma.$WorkspacePayload<ExtArgs>;
        creator: Prisma.$UserPayload<ExtArgs>;
    };
    scalars: runtime.Types.Extensions.GetPayloadResult<{
        id: string;
        title: string;
        startTime: Date;
        endTime: Date;
        workspaceId: string;
        creatorId: string;
        createdAt: Date;
        updatedAt: Date;
    }, ExtArgs["result"]["event"]>;
    composites: {};
};
export type EventGetPayload<S extends boolean | null | undefined | EventDefaultArgs> = runtime.Types.Result.GetResult<Prisma.$EventPayload, S>;
export type EventCountArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = Omit<EventFindManyArgs, 'select' | 'include' | 'distinct' | 'omit'> & {
    select?: EventCountAggregateInputType | true;
};
export interface EventDelegate<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> {
    [K: symbol]: {
        types: Prisma.TypeMap<ExtArgs>['model']['Event'];
        meta: {
            name: 'Event';
        };
    };
    findUnique<T extends EventFindUniqueArgs>(args: Prisma.SelectSubset<T, EventFindUniqueArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findUnique", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findUniqueOrThrow<T extends EventFindUniqueOrThrowArgs>(args: Prisma.SelectSubset<T, EventFindUniqueOrThrowArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findFirst<T extends EventFindFirstArgs>(args?: Prisma.SelectSubset<T, EventFindFirstArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findFirst", GlobalOmitOptions> | null, null, ExtArgs, GlobalOmitOptions>;
    findFirstOrThrow<T extends EventFindFirstOrThrowArgs>(args?: Prisma.SelectSubset<T, EventFindFirstOrThrowArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findFirstOrThrow", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    findMany<T extends EventFindManyArgs>(args?: Prisma.SelectSubset<T, EventFindManyArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "findMany", GlobalOmitOptions>>;
    create<T extends EventCreateArgs>(args: Prisma.SelectSubset<T, EventCreateArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "create", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    createMany<T extends EventCreateManyArgs>(args?: Prisma.SelectSubset<T, EventCreateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    createManyAndReturn<T extends EventCreateManyAndReturnArgs>(args?: Prisma.SelectSubset<T, EventCreateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "createManyAndReturn", GlobalOmitOptions>>;
    delete<T extends EventDeleteArgs>(args: Prisma.SelectSubset<T, EventDeleteArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "delete", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    update<T extends EventUpdateArgs>(args: Prisma.SelectSubset<T, EventUpdateArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "update", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    deleteMany<T extends EventDeleteManyArgs>(args?: Prisma.SelectSubset<T, EventDeleteManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateMany<T extends EventUpdateManyArgs>(args: Prisma.SelectSubset<T, EventUpdateManyArgs<ExtArgs>>): Prisma.PrismaPromise<Prisma.BatchPayload>;
    updateManyAndReturn<T extends EventUpdateManyAndReturnArgs>(args: Prisma.SelectSubset<T, EventUpdateManyAndReturnArgs<ExtArgs>>): Prisma.PrismaPromise<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "updateManyAndReturn", GlobalOmitOptions>>;
    upsert<T extends EventUpsertArgs>(args: Prisma.SelectSubset<T, EventUpsertArgs<ExtArgs>>): Prisma.Prisma__EventClient<runtime.Types.Result.GetResult<Prisma.$EventPayload<ExtArgs>, T, "upsert", GlobalOmitOptions>, never, ExtArgs, GlobalOmitOptions>;
    count<T extends EventCountArgs>(args?: Prisma.Subset<T, EventCountArgs>): Prisma.PrismaPromise<T extends runtime.Types.Utils.Record<'select', any> ? T['select'] extends true ? number : Prisma.GetScalarType<T['select'], EventCountAggregateOutputType> : number>;
    aggregate<T extends EventAggregateArgs>(args: Prisma.Subset<T, EventAggregateArgs>): Prisma.PrismaPromise<GetEventAggregateType<T>>;
    groupBy<T extends EventGroupByArgs, HasSelectOrTake extends Prisma.Or<Prisma.Extends<'skip', Prisma.Keys<T>>, Prisma.Extends<'take', Prisma.Keys<T>>>, OrderByArg extends Prisma.True extends HasSelectOrTake ? {
        orderBy: EventGroupByArgs['orderBy'];
    } : {
        orderBy?: EventGroupByArgs['orderBy'];
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
    }[OrderFields]>(args: Prisma.SubsetIntersection<T, EventGroupByArgs, OrderByArg> & InputErrors): {} extends InputErrors ? GetEventGroupByPayload<T> : Prisma.PrismaPromise<InputErrors>;
    readonly fields: EventFieldRefs;
}
export interface Prisma__EventClient<T, Null = never, ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs, GlobalOmitOptions = {}> extends Prisma.PrismaPromise<T> {
    readonly [Symbol.toStringTag]: "PrismaPromise";
    workspace<T extends Prisma.WorkspaceDefaultArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.WorkspaceDefaultArgs<ExtArgs>>): Prisma.Prisma__WorkspaceClient<runtime.Types.Result.GetResult<Prisma.$WorkspacePayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions> | Null, Null, ExtArgs, GlobalOmitOptions>;
    creator<T extends Prisma.UserDefaultArgs<ExtArgs> = {}>(args?: Prisma.Subset<T, Prisma.UserDefaultArgs<ExtArgs>>): Prisma.Prisma__UserClient<runtime.Types.Result.GetResult<Prisma.$UserPayload<ExtArgs>, T, "findUniqueOrThrow", GlobalOmitOptions> | Null, Null, ExtArgs, GlobalOmitOptions>;
    then<TResult1 = T, TResult2 = never>(onfulfilled?: ((value: T) => TResult1 | PromiseLike<TResult1>) | undefined | null, onrejected?: ((reason: any) => TResult2 | PromiseLike<TResult2>) | undefined | null): runtime.Types.Utils.JsPromise<TResult1 | TResult2>;
    catch<TResult = never>(onrejected?: ((reason: any) => TResult | PromiseLike<TResult>) | undefined | null): runtime.Types.Utils.JsPromise<T | TResult>;
    finally(onfinally?: (() => void) | undefined | null): runtime.Types.Utils.JsPromise<T>;
}
export interface EventFieldRefs {
    readonly id: Prisma.FieldRef<"Event", 'String'>;
    readonly title: Prisma.FieldRef<"Event", 'String'>;
    readonly startTime: Prisma.FieldRef<"Event", 'DateTime'>;
    readonly endTime: Prisma.FieldRef<"Event", 'DateTime'>;
    readonly workspaceId: Prisma.FieldRef<"Event", 'String'>;
    readonly creatorId: Prisma.FieldRef<"Event", 'String'>;
    readonly createdAt: Prisma.FieldRef<"Event", 'DateTime'>;
    readonly updatedAt: Prisma.FieldRef<"Event", 'DateTime'>;
}
export type EventFindUniqueArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    where: Prisma.EventWhereUniqueInput;
};
export type EventFindUniqueOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    where: Prisma.EventWhereUniqueInput;
};
export type EventFindFirstArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
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
export type EventFindFirstOrThrowArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
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
export type EventFindManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
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
export type EventCreateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.EventCreateInput, Prisma.EventUncheckedCreateInput>;
};
export type EventCreateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.EventCreateManyInput | Prisma.EventCreateManyInput[];
    skipDuplicates?: boolean;
};
export type EventCreateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelectCreateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    data: Prisma.EventCreateManyInput | Prisma.EventCreateManyInput[];
    skipDuplicates?: boolean;
    include?: Prisma.EventIncludeCreateManyAndReturn<ExtArgs> | null;
};
export type EventUpdateArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    data: Prisma.XOR<Prisma.EventUpdateInput, Prisma.EventUncheckedUpdateInput>;
    where: Prisma.EventWhereUniqueInput;
};
export type EventUpdateManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    data: Prisma.XOR<Prisma.EventUpdateManyMutationInput, Prisma.EventUncheckedUpdateManyInput>;
    where?: Prisma.EventWhereInput;
    limit?: number;
};
export type EventUpdateManyAndReturnArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelectUpdateManyAndReturn<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    data: Prisma.XOR<Prisma.EventUpdateManyMutationInput, Prisma.EventUncheckedUpdateManyInput>;
    where?: Prisma.EventWhereInput;
    limit?: number;
    include?: Prisma.EventIncludeUpdateManyAndReturn<ExtArgs> | null;
};
export type EventUpsertArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    where: Prisma.EventWhereUniqueInput;
    create: Prisma.XOR<Prisma.EventCreateInput, Prisma.EventUncheckedCreateInput>;
    update: Prisma.XOR<Prisma.EventUpdateInput, Prisma.EventUncheckedUpdateInput>;
};
export type EventDeleteArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
    where: Prisma.EventWhereUniqueInput;
};
export type EventDeleteManyArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    where?: Prisma.EventWhereInput;
    limit?: number;
};
export type EventDefaultArgs<ExtArgs extends runtime.Types.Extensions.InternalArgs = runtime.Types.Extensions.DefaultArgs> = {
    select?: Prisma.EventSelect<ExtArgs> | null;
    omit?: Prisma.EventOmit<ExtArgs> | null;
    include?: Prisma.EventInclude<ExtArgs> | null;
};
