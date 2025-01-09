"use client"

import {Button} from '@/components/ui/button';
import Link from "next/link";
import React, {useState} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";

interface IFormInput {
    firstName: string
    lastName: string
    age: number
}

export default function sellPost() {
    const [formstate, setFormstate] = useState(0);
    const {register, handleSubmit} = useForm<IFormInput>()
    const onSubmit: SubmitHandler<IFormInput> = (data) => console.log(data)
    return (
        <div>
            <Link href={'/sellPost/form1'}>
                <Button>
                    Sell Post
                </Button>
            </Link>
            <form onSubmit={handleSubmit(onSubmit)}>
                <input {...register("firstName", {required: true, maxLength: 20})} />
                <input {...register("lastName", {pattern: /^[A-Za-z]+$/i})} />
                <input type="number" {...register("age", {min: 18, max: 99})} />
                <input type="submit"/>
            </form>
        </div>
    )
}